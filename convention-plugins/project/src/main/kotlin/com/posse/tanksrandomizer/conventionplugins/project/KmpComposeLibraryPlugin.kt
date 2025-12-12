package com.posse.tanksrandomizer.conventionplugins.project

import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("unused")
class KmpComposeLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("android.library.plugin")
                apply("kmp.compose.config")
                apply("kmp.base.test.config")
            }
        }
    }
}
