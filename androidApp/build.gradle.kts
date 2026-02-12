@file:Suppress("UnstableApiUsage")

import com.android.build.api.dsl.ApplicationExtension
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.google.firebase.crashlytics)
}

configure<ApplicationExtension> {
    configureAndroidAppVersion()
    configureAndroidOptions()
    configureAndroidAppSigning()
    configureAndroidAppBuildTypes()
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
    implementation(libs.androidx.lifecycle.runtime.compose)
}

private fun ApplicationExtension.configureAndroidAppVersion() {
    namespace = libs.versions.namespace.get()
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()

        applicationId = libs.versions.namespace.get()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

private fun ApplicationExtension.configureAndroidAppBuildTypes() {
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
        }
    }
}

private fun ApplicationExtension.configureAndroidOptions() {
    buildFeatures {
        buildConfig = true
    }

    bundle {
        // Отключить динамическую доставку ресурсов по локалям
        language.enableSplit = false
        density.enableSplit = true
        abi.enableSplit = true
    }

    androidResources {
        localeFilters.addAll(setOf("en", "ru"))
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    packaging {
        resources {
            excludes += listOf(
                "/META-INF/{AL2.0,LGPL2.1}",
                "/kotlin/**",
                "/META-INF/androidx.*.version",
                "/META-INF/com.google.*.version",
                "/META-INF/kotlinx_*.version",
                "/kotlin-tooling-metadata.json",
                "/DebugProbesKt.bin",
                "/META-INF/com/android/build/gradle/app-metadata.properties",
                "/*.properties",
                "/*.proto",
                "/junit/**",
                "/LICENSE-junit.txt"
            )
        }
    }
}

private fun ApplicationExtension.configureAndroidAppSigning() {
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

    tasks.withType<Zip>().configureEach {
        if (name.startsWith("package") || name.startsWith("bundle")) {
            val variantName = when {
                name.startsWith("package") -> name.removePrefix("package")
                name.startsWith("bundle") -> name.removePrefix("bundle")
                else -> return@configureEach
            }

            val versionName = libs.versions.versionName.get()
            val outputName = buildString {
                append("app-")
                if (variantName.isNotEmpty()) {
                    append(variantName.lowercase())
                    append("-")
                }
                append("v$versionName")
            }

            archiveBaseName.set(outputName)
            archiveVersion.set(versionName)
            archiveClassifier.set(variantName.lowercase())
        }
    }
}
