package com.posse.tanksrandomizer.conventionplugins.project

import com.android.build.api.dsl.ApplicationDefaultConfig
import com.posse.tanksrandomizer.conventionplugins.base.extensions.androidApplication
import com.posse.tanksrandomizer.conventionplugins.base.extensions.androidConfig
import com.posse.tanksrandomizer.conventionplugins.base.extensions.implementation
import com.posse.tanksrandomizer.conventionplugins.base.extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.internal.extensions.stdlib.capitalized
import org.gradle.kotlin.dsl.dependencies
import java.io.File
import java.util.Properties

@Suppress("unused")
class AndroidApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.plugins.android.application.get().pluginId)
                apply("android.base.config")
                apply("android.base.test.config")
                apply("android.firebaseCrashlytics.plugin")
            }

            androidConfig {
                defaultConfig {
                    this as ApplicationDefaultConfig

                    targetSdk = libs.versions.targetSdk.get().toInt()

                    versionCode = libs.versions.appVersionCode.get().toInt()
                    versionName = libs.versions.appVersionName.get()
                }
            }

            androidApplication {
                dependencies {
                    implementation(libs.androidx.splashscreen)
                    implementation(libs.android.material.theme)
                    implementation(libs.androidx.lifecycle.service)
                    implementation(libs.androidx.material3)
                }
            }

            androidApplication {
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
//            applicationIdSuffix = ".debug"
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
        }
    }
}
