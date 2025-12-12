package com.posse.tanksrandomizer.conventionplugins.project.extensions

import com.posse.tanksrandomizer.conventionplugins.base.extensions.kotlinMultiplatformConfig
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

fun Project.commonMainDependencies(block: KotlinDependencyHandler.() -> Unit) {
    kotlinMultiplatformConfig {
        sourceSets.commonMain.dependencies(block)
    }
}

@Suppress("unused")
fun Project.commonTestDependencies(block: KotlinDependencyHandler.() -> Unit) {
    kotlinMultiplatformConfig {
        sourceSets.commonTest.dependencies(block)
    }
}

fun Project.androidMainDependencies(block: KotlinDependencyHandler.() -> Unit) {
    kotlinMultiplatformConfig {
        sourceSets.androidMain.dependencies(block)
    }
}

fun Project.jvmMainDependencies(block: KotlinDependencyHandler.() -> Unit) {
    kotlinMultiplatformConfig {
        sourceSets.jvmMain.dependencies(block)
    }
}

@Suppress("unused")
fun Project.iosMainDependencies(block: KotlinDependencyHandler.() -> Unit) {
    kotlinMultiplatformConfig {
        sourceSets.iosMain.dependencies(block)
    }
}
