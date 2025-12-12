package com.posse.tanksrandomizer.conventionplugins.project

import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("unused")
class KmpComposeApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("android.application.plugin")
                apply("kmp.application.plugin")
                apply("kmp.compose.config")
                apply("kmp.base.test.config")
                apply("kmp.compose.localina.plugin")
                apply("kmp.compose.haze.plugin")
                apply("kmp.compose.webView.plugin")
            }
        }
    }
}
