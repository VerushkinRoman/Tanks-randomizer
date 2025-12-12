package com.posse.tanksrandomizer.conventionplugins.project

import com.posse.tanksrandomizer.conventionplugins.project.extensions.commonMainDependencies
import com.posse.tanksrandomizer.conventionplugins.base.extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("unused")
class KmpDateTimePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            commonMainDependencies {
                implementation(libs.kotlinx.datetime)
            }
        }
    }
}
