import com.posse.tanksrandomizer.conventionplugins.base.extensions.androidApplication
import com.posse.tanksrandomizer.conventionplugins.project.extensions.composeDesktopApplication
import com.posse.tanksrandomizer.conventionplugins.project.extensions.iosRegularFramework

plugins {
    id("kmp.compose.application.plugin")
    id("kmp.coroutines.plugin")
    id("kmp.serialization.plugin")
    id("kmp.coil.plugin")
    id("kmp.ktor.plugin")
    id("kmp.multiplatformSettings.plugin")
    id("kmp.datetime.plugin")
    id("kmp.kodein.plugin")
    id("kmp.room.plugin")
    id("kmp.buildKonfig.plugin")
}

androidApplication {
    namespace = "com.posse.tanksrandomizer"

    defaultConfig {
        applicationId = "com.posse.tanksrandomizer"
    }
}

iosRegularFramework {
    baseName = "ComposeApp"
    isStatic = true
}

composeDesktopApplication(
    mainClass = "MainKt",
    packageName = "Random Tank Generator",
)
