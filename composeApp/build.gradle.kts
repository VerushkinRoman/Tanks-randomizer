import org.gradle.internal.extensions.stdlib.capitalized
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import java.util.Properties

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose)
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlinx.serialization)
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

            export(libs.deeplink)
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
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.androidx.navigation.composee)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.datetime)
            implementation(libs.multiplatformSettings.noArgs)
            implementation(libs.kodein)
            api(libs.deeplink)
            implementation(libs.deeplink.compose)
        }

        androidMain.dependencies {
            implementation(libs.androidx.activityCompose)
            implementation(libs.kotlinx.coroutines.android)
            implementation(libs.androidx.splashscreen)
            implementation(libs.android.material.theme)
            implementation(libs.multiplatformSettings)
            implementation(libs.multiplatformSettings.serialization)
            implementation(libs.multiplatformSettings.coroutines)
        }

        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

android {
    namespace = "com.posse.tanksrandomizer"
    compileSdk = 35

    defaultConfig {
        minSdk = 23
        targetSdk = 35

        applicationId = "com.posse.tanksrandomizer"
        versionCode = 8
        versionName = "1.5.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        buildConfig = true
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
            packageVersion = "1.5.0"

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
            configurationFiles.from(project.file("proguard-rules.pro"))
        }
    }
}
