package com.posse.tanksrandomizer.conventionplugins.project

import com.posse.tanksrandomizer.conventionplugins.base.extensions.androidConfig
import com.posse.tanksrandomizer.conventionplugins.base.extensions.implementation
import com.posse.tanksrandomizer.conventionplugins.base.extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

@Suppress("unused")
class AndroidFirebaseCrashlyticsPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.plugins.google.gms.google.services.get().pluginId)
                apply(libs.plugins.google.firebase.crashlytics.get().pluginId)
            }

            androidConfig {
                dependencies {
                    implementation(libs.firebase.crashlytics)
                }
            }
        }
    }
}
