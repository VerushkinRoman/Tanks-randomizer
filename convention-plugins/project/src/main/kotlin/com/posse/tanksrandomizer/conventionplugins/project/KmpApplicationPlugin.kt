package com.posse.tanksrandomizer.conventionplugins.project

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.JavaExec
import org.gradle.kotlin.dsl.withType

@Suppress("unused")
class KmpApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
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
            }
        }
    }
}
