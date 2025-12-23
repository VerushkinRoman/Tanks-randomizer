package com.posse.tanksrandomizer.conventionplugins.project

import com.posse.tanksrandomizer.conventionplugins.base.extensions.libs
import com.posse.tanksrandomizer.conventionplugins.project.extensions.commonMainDependencies
import com.posse.tanksrandomizer.conventionplugins.project.extensions.configureRoom
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

@Suppress("unused")
class KmpRoomPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.plugins.ksp.get().pluginId)
                apply(libs.plugins.androidx.room.get().pluginId)
            }

            commonMainDependencies {
                implementation(libs.androidx.room.runtime)
                implementation(libs.androidx.sqlite.bundled)
            }

            this.dependencies {
                add("kspAndroid", libs.androidx.room.compiler)
                add("kspIosSimulatorArm64", libs.androidx.room.compiler)
                add("kspIosX64", libs.androidx.room.compiler)
                add("kspIosArm64", libs.androidx.room.compiler)
                add("kspJvm", libs.androidx.room.compiler)
            }

            configureRoom()
        }
    }
}