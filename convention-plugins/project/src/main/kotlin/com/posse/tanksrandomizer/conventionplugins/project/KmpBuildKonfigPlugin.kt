package com.posse.tanksrandomizer.conventionplugins.project

import com.posse.tanksrandomizer.conventionplugins.base.extensions.libs
import com.posse.tanksrandomizer.conventionplugins.project.extensions.configureBuildKonfig
import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("unused")
class KmpBuildKonfigPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply(libs.plugins.buildkonfig.get().pluginId)
            }

            configureBuildKonfig(
                packageName = "com.posse.tanksrandomizer.common.data.di",
                buildConfigFields = listOf(Pair("APPLICATION_ID", "appication_id"))
            )
        }
    }
}
