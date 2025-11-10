import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import org.gradle.internal.extensions.stdlib.capitalized
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import java.util.Properties

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose)
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.buildkonfig)
    alias(libs.plugins.realmDB)
}

kotlin {
    jvmToolchain(17)
    androidTarget()

    jvm()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    compilerOptions {
        freeCompilerArgs.addAll(
            listOf(
                "-Xno-call-assertions",
                "-Xno-receiver-assertions",
                "-Xno-param-assertions",
                "-Xexpect-actual-classes",
            )
        )
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.components.resources)
            implementation(compose.materialIconsExtended)
            implementation(libs.window.size.clazz)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.androidx.lifecycle.viewmodel.compose)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.androidx.navigation.composee)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.datetime)
            implementation(libs.multiplatformSettings.noArgs)
            implementation(libs.multiplatformSettings)
            implementation(libs.multiplatformSettings.serialization)
            implementation(libs.multiplatformSettings.coroutines)
            implementation(libs.kodein)
            implementation(libs.haze)
            implementation(libs.haze.materials)
            implementation(libs.ktor.core)
            implementation(libs.ktor.client.cio)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.logging)
            implementation(libs.realmDB)
            implementation(libs.coil.compose)
            implementation(libs.coil.network)
            implementation(libs.localina)
            api(libs.compose.webview.multiplatform) // use api since the desktop app need to access the Cef to initialize it.
        }

        androidMain.dependencies {
            implementation(libs.androidx.activityCompose)
            implementation(libs.kotlinx.coroutines.android)
            implementation(libs.androidx.splashscreen)
            implementation(libs.android.material.theme)
            implementation(libs.androidx.lifecycle.service)
            implementation(libs.androidx.material3)
        }

        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

android {
    namespace = "com.posse.tanksrandomizer"
    compileSdk = 36

    defaultConfig {
        minSdk = 23
        targetSdk = 36

        applicationId = "com.posse.tanksrandomizer"
        versionCode = 9
        versionName = "2.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        buildConfig = true
    }

    bundle {
        @Suppress("SpellCheckingInspection", "UnstableApiUsage")
        // Отключить динамическую доставку ресурсов по локалям
        language.enableSplit = false
        @Suppress("UnstableApiUsage")
        density.enableSplit = true
        @Suppress("UnstableApiUsage")
        abi.enableSplit = true
    }

    androidResources {
        @Suppress("UnstableApiUsage")
        localeFilters += listOf("en", "ru")
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/kotlin/**"
            excludes += "/META-INF/androidx.*.version"
            excludes += "/META-INF/com.google.*.version"
            excludes += "/META-INF/kotlinx_*.version"
            excludes += "/kotlin-tooling-metadata.json"
            excludes += "/DebugProbesKt.bin"
            excludes += "/META-INF/com/android/build/gradle/app-metadata.properties"
            excludes += "/*.properties"
            excludes += "/*.proto"
            excludes += "/junit/**"
            excludes += "/LICENSE-junit.txt"
        }
    }

    signingConfigs {
        create("release") {
            val properties = Properties().apply {
                load(File(projectDir, "signingKey.properties").reader())
            }

            storeFile = File(properties.getProperty("path_to_store_file"))
            storePassword = properties.getProperty("store_password")
            keyAlias = properties.getProperty("key_alias")
            keyPassword = properties.getProperty("key_password")
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")

            isMinifyEnabled = true
            isShrinkResources = true

            ndk {
                debugSymbolLevel = "FULL"
            }

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            signingConfig = signingConfigs.getByName("release")
            versionNameSuffix = "-debug"
            applicationIdSuffix = ".debug"
        }
    }

    applicationVariants.all {
        outputs.forEach { output ->
            val bundleFinalizeTaskName = StringBuilder("sign").run {
                productFlavors.forEach {
                    append(it.name.capitalized())
                }
                append(buildType.name.capitalized())
                append("Bundle")
                toString()
            }

            val outputName = buildString {
                append("app-")
                productFlavors.forEach {
                    append(it.name)
                    append("-")
                }
                append("v$versionName")
            }

            tasks.named(
                bundleFinalizeTaskName,
                com.android.build.gradle.internal.tasks.FinalizeBundleTask::class.java
            ) {
                val file = finalBundleFile.asFile.get()
                val finalFile = File(file.parentFile, "$outputName.aab")
                finalBundleFile.set(finalFile)
            }
            if (output is com.android.build.gradle.internal.api.BaseVariantOutputImpl) {
                output.outputFileName = "$outputName.${output.outputFile.extension}"
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Random Tank Generator"
            packageVersion = "2.0.0"

            linux {
                iconFile.set(project.file("desktopAppIcons/LinuxIcon.png"))
            }
            windows {
                iconFile.set(project.file("desktopAppIcons/WindowsIcon.ico"))
            }
            macOS {
                iconFile.set(project.file("desktopAppIcons/MacosIcon.icns"))
                bundleID = "com.posse.tanksrandomizer"
            }
        }

        buildTypes.release.proguard {
            obfuscate.set(true)
            optimize.set(false)
            configurationFiles.from(
                project.file("proguard-rules.pro"),
                project.file("compose-desktop.pro"),
            )
        }
    }
}

afterEvaluate {
    tasks.withType<JavaExec> {
        jvmArgs("--add-opens", "java.desktop/sun.awt=ALL-UNNAMED")
        jvmArgs("--add-opens", "java.desktop/java.awt.peer=ALL-UNNAMED")

        if (System.getProperty("os.name").contains("Mac")) {
            jvmArgs("--add-opens", "java.desktop/sun.awt=ALL-UNNAMED")
            jvmArgs("--add-opens", "java.desktop/sun.lwawt=ALL-UNNAMED")
            jvmArgs("--add-opens", "java.desktop/sun.lwawt.macosx=ALL-UNNAMED")
        }
    }
}

buildkonfig {
    packageName = "com.posse.tanksrandomizer.common.data.di"
    exposeObjectWithName = "ServerConstants"

    val properties = Properties().apply {
        load(File(projectDir, "serverConstants.properties").reader())
    }

    defaultConfigs {
        buildConfigField(STRING, "APPLICATION_ID", properties.getProperty("appication_id"))
    }
}
