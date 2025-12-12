import com.posse.tanksrandomizer.conventionplugins.base.extensions.libs

plugins {
    id("org.jetbrains.kotlin.plugin.compose")
    id("org.jetbrains.compose")

    id("kmp.base.config")
    id("android.compose.config")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.material.icons.extended)
            implementation(libs.compose.window.size.clazz)
            implementation(libs.compose.androidx.lifecycle.runtime)

            implementation(libs.androidx.lifecycle.viewmodel.navigation3)
            implementation(libs.jetbrains.navigation3.ui)

            implementation(libs.kodein.compose)
        }

        androidMain.dependencies {
            implementation(libs.androidx.activityCompose)
        }

        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
        }
    }
}
