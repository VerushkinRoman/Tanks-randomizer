@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import com.posse.tanksrandomizer.conventionplugins.base.extensions.kotlinAndroidTarget
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree

kotlinAndroidTarget {
    instrumentedTestVariant {
        sourceSetTree.set(KotlinSourceSetTree.test)

        dependencies {
//            debugImplementation(libs.androidx.testManifest)
//            implementation(libs.androidx.junit4)
        }
    }
}
