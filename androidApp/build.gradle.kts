import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.internal.extensions.stdlib.capitalized
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.google.firebase.crashlytics)
}

android {
    configureAndroidAppVersion()
    configureAndroidOptions()
    configureAndroidAppSigning()
    configureAndroidAppBuildTypes()
    configureAndroidAppArtifactNames()
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(libs.compose.foundation)
    implementation(libs.compose.material3)
    implementation(libs.compose.components.resources)
    implementation(libs.compose.material.icons.extended)
    implementation(libs.androidx.lifecycle.viewmodel.navigation3)
    implementation(libs.androidx.activityCompose)
    implementation(libs.androidx.splashscreen)
    implementation(libs.android.material.theme)
    implementation(libs.androidx.lifecycle.service)
    implementation(libs.firebase.crashlytics)
    implementation(project(":composeApp"))
}

private fun BaseAppModuleExtension.configureAndroidAppVersion() {
    namespace = "com.posse.tanksrandomizer"
    compileSdk = 36

    defaultConfig {
        minSdk = 23
        targetSdk = 36

        applicationId = "com.posse.tanksrandomizer"
        versionCode = 12
        versionName = "2.1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

private fun BaseAppModuleExtension.configureAndroidAppBuildTypes() {
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
                rootProject.file("proguard-rules.pro"),
            )
        }

        debug {
            signingConfig = signingConfigs.getByName("release")
            versionNameSuffix = "-debug"
//            applicationIdSuffix = ".debug"
        }
    }
}

private fun BaseAppModuleExtension.configureAndroidOptions() {
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
}

private fun BaseAppModuleExtension.configureAndroidAppSigning() {
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

private fun BaseAppModuleExtension.configureAndroidAppArtifactNames() {
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
