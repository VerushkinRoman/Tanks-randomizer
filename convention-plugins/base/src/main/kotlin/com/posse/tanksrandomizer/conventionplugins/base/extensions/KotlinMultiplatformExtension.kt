package com.posse.tanksrandomizer.conventionplugins.base.extensions

import org.gradle.api.Project
import org.gradle.kotlin.dsl.findByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinAndroidTarget

fun Project.kotlinMultiplatformConfig(block: KotlinMultiplatformExtension.() -> Unit) {
    extensions.findByType<KotlinMultiplatformExtension>()
        ?.apply(block)
        ?: error("Kotlin multiplatform was not been added")
}

fun Project.kotlinAndroidTarget(block: KotlinAndroidTarget.() -> Unit) {
    kotlinMultiplatformConfig {
        androidTarget(block)
    }
}
