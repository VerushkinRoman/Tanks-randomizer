package com.posse.tanksrandomizer.conventionplugins.project

import com.posse.tanksrandomizer.conventionplugins.project.extensions.commonMainDependencies
import com.posse.tanksrandomizer.conventionplugins.base.extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("unused")
class KmpRealmDbPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.plugins.realmDB.get().pluginId)
            }

            commonMainDependencies {
                implementation(libs.realmDB)
            }
        }
    }
}
