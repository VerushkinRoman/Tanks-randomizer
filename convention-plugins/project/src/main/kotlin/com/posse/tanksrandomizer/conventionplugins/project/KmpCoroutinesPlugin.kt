package com.posse.tanksrandomizer.conventionplugins.project

import org.gradle.api.Plugin
import org.gradle.api.Project
import com.posse.tanksrandomizer.conventionplugins.project.extensions.androidMainDependencies
import com.posse.tanksrandomizer.conventionplugins.project.extensions.commonMainDependencies
import com.posse.tanksrandomizer.conventionplugins.project.extensions.jvmMainDependencies
import com.posse.tanksrandomizer.conventionplugins.base.extensions.libs

@Suppress("unused")
class KmpCoroutinesPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            commonMainDependencies {
                implementation(libs.kotlinx.coroutines.core)
            }

            androidMainDependencies {
                implementation(libs.kotlinx.coroutines.android)
            }

            jvmMainDependencies {
                implementation(libs.kotlinx.coroutines.swing)
            }
        }
    }
}
