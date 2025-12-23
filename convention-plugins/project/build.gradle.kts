plugins {
    `kotlin-dsl`
}

group = "com.posse.tanksrandomizer.conventionplugins.project"

dependencies {
    implementation(libs.gradleplugin.android)
    implementation(libs.gradleplugin.kotlin)
    implementation(libs.gradleplugin.compose)
    implementation(libs.gradleplugin.composeCompiler)
    implementation(libs.gradleplugin.buildkonfig)
    implementation(libs.gradleplugin.buildkonfig.kompiler)
    implementation(libs.gradleplugin.androidx.room)
    // Workaround for version catalog working inside precompiled scripts
    // Issue - https://github.com/gradle/gradle/issues/15383
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))

    implementation(libs.gradleplugin.base)
}

gradlePlugin {
    plugins {
        register("android.application.plugin") {
            id = "android.application.plugin"
            implementationClass = "com.posse.tanksrandomizer.conventionplugins.project.AndroidApplicationPlugin"
        }

        register("android.library.plugin") {
            id = "android.library.plugin"
            implementationClass = "com.posse.tanksrandomizer.conventionplugins.project.AndroidLibraryPlugin"
        }

        register("kmp.compose.application.plugin") {
            id = "kmp.compose.application.plugin"
            implementationClass = "com.posse.tanksrandomizer.conventionplugins.project.KmpComposeApplicationPlugin"
        }

        register("kmp.compose.library.plugin") {
            id = "kmp.compose.library.plugin"
            implementationClass = "com.posse.tanksrandomizer.conventionplugins.project.KmpComposeLibraryPlugin"
        }

        register("kmp.coroutines.plugin") {
            id = "kmp.coroutines.plugin"
            implementationClass = "com.posse.tanksrandomizer.conventionplugins.project.KmpCoroutinesPlugin"
        }

        register("kmp.serialization.plugin") {
            id = "kmp.serialization.plugin"
            implementationClass = "com.posse.tanksrandomizer.conventionplugins.project.KmpSerializationPlugin"
        }

        register("kmp.coil.plugin") {
            id = "kmp.coil.plugin"
            implementationClass = "com.posse.tanksrandomizer.conventionplugins.project.KmpCoilPlugin"
        }

        register("kmp.ktor.plugin") {
            id = "kmp.ktor.plugin"
            implementationClass = "com.posse.tanksrandomizer.conventionplugins.project.KmpKtorPlugin"
        }

        register("kmp.multiplatformSettings.plugin") {
            id = "kmp.multiplatformSettings.plugin"
            implementationClass = "com.posse.tanksrandomizer.conventionplugins.project.KmpMultiplatformSettingsPlugin"
        }

        register("android.firebaseCrashlytics.plugin") {
            id = "android.firebaseCrashlytics.plugin"
            implementationClass = "com.posse.tanksrandomizer.conventionplugins.project.AndroidFirebaseCrashlyticsPlugin"
        }

        register("kmp.application.plugin") {
            id = "kmp.application.plugin"
            implementationClass = "com.posse.tanksrandomizer.conventionplugins.project.KmpApplicationPlugin"
        }

        register("kmp.datetime.plugin") {
            id = "kmp.datetime.plugin"
            implementationClass = "com.posse.tanksrandomizer.conventionplugins.project.KmpDateTimePlugin"
        }

        register("kmp.kodein.plugin") {
            id = "kmp.kodein.plugin"
            implementationClass = "com.posse.tanksrandomizer.conventionplugins.project.KmpKodeinPlugin"
        }

        register("kmp.room.plugin") {
            id = "kmp.room.plugin"
            implementationClass = "com.posse.tanksrandomizer.conventionplugins.project.KmpRoomPlugin"
        }

        register("kmp.compose.localina.plugin") {
            id = "kmp.compose.localina.plugin"
            implementationClass = "com.posse.tanksrandomizer.conventionplugins.project.KmpComposeLocalinaPlugin"
        }

        register("kmp.compose.haze.plugin") {
            id = "kmp.compose.haze.plugin"
            implementationClass = "com.posse.tanksrandomizer.conventionplugins.project.KmpComposeHazePlugin"
        }

        register("kmp.compose.webView.plugin") {
            id = "kmp.compose.webView.plugin"
            implementationClass = "com.posse.tanksrandomizer.conventionplugins.project.KmpComposeWebViewPlugin"
        }

        register("kmp.buildKonfig.plugin") {
            id = "kmp.buildKonfig.plugin"
            implementationClass = "com.posse.tanksrandomizer.conventionplugins.project.KmpBuildKonfigPlugin"
        }
    }
}
