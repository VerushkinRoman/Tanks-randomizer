import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import java.util.Properties

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.buildkonfig)
    alias(libs.plugins.androidx.room)
    alias(libs.plugins.ksp)
}

kotlin {
    configureTargets()
    configureOptions()
    configureDependencies()
}

configureRoom()

private fun KotlinMultiplatformExtension.configureDependencies() {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.jetbrains.navigation3.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.material.icons.extended)
            implementation(libs.compose.window.size.clazz)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.androidx.lifecycle.viewmodel.navigation3)
            implementation(libs.compose.androidx.lifecycle.runtime)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.datetime)
            implementation(libs.multiplatformSettings.noArgs)
            implementation(libs.multiplatformSettings)
            implementation(libs.multiplatformSettings.serialization)
            implementation(libs.multiplatformSettings.coroutines)
            implementation(libs.multiplatformSettings.makeObservable)
            implementation(libs.kodein)
            implementation(libs.kodein.compose)
            implementation(libs.compose.haze)
            implementation(libs.compose.haze.materials)
            implementation(libs.ktor.core)
            implementation(libs.ktor.client.cio)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.logging)
            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)
            implementation(libs.compose.localina)
            implementation(libs.androidx.sqlite.bundled)
            implementation(libs.androidx.room.runtime)
            api(libs.compose.webview.multiplatform) // use api since the desktop app need to access the Cef to initialize it.
        }

        androidMain.dependencies {
            implementation(libs.androidx.activityCompose)
            implementation(libs.kotlinx.coroutines.android)
            implementation(libs.androidx.splashscreen)
            implementation(libs.android.material.theme)
            implementation(libs.androidx.lifecycle.service)
            implementation(libs.androidx.material3)
            implementation(libs.firebase.crashlytics)
        }

        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

private fun KotlinMultiplatformAndroidLibraryTarget.configureAndroidAppVersion() {
    namespace = "com.posse.tanksrandomizer.composeApp"
    compileSdk = 36
    minSdk = 23
}

private fun KotlinMultiplatformExtension.configureTargets() {
    android {
        configureAndroidAppVersion()
        configureAndroidOptions()
    }

    jvm()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
}

private fun KotlinMultiplatformExtension.configureOptions() {
    jvmToolchain(17)

    compilerOptions {
        freeCompilerArgs.addAll(
            listOf(
                "-Xno-call-assertions",
                "-Xno-receiver-assertions",
                "-Xno-param-assertions",
                "-Xexpect-actual-classes",
            )
        )
    }
}

private fun KotlinMultiplatformAndroidLibraryTarget.configureAndroidOptions() {
    experimentalProperties["android.experimental.kmp.enableAndroidResources"] = true

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/kotlin/**"
            excludes += "/META-INF/androidx.*.version"
            excludes += "/META-INF/com.google.*.version"
            excludes += "/META-INF/kotlinx_*.version"
            excludes += "/kotlin-tooling-metadata.json"
            excludes += "/DebugProbesKt.bin"
            excludes += "/META-INF/com/android/build/gradle/app-metadata.properties"
            excludes += "/*.properties"
            excludes += "/*.proto"
            excludes += "/junit/**"
            excludes += "/LICENSE-junit.txt"
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Random Tank Generator"
            packageVersion = "2.1.0"

            linux {
                iconFile.set(project.file("desktopAppIcons/LinuxIcon.png"))
            }
            windows {
                iconFile.set(project.file("desktopAppIcons/WindowsIcon.ico"))
            }
            macOS {
                iconFile.set(project.file("desktopAppIcons/MacosIcon.icns"))
                bundleID = "com.posse.tanksrandomizer"
            }
        }

        buildTypes.release.proguard {
            obfuscate.set(true)
            optimize.set(false)
            configurationFiles.from(
                rootProject.file("proguard-rules.pro"),
                project.file("compose-desktop.pro"),
            )
        }
    }
}

private fun Project.configureRoom() {
    room {
        schemaDirectory("$projectDir/schemas")
    }

    dependencies {
        add("kspAndroid", libs.androidx.room.compiler)
        add("kspIosSimulatorArm64", libs.androidx.room.compiler)
        add("kspIosX64", libs.androidx.room.compiler)
        add("kspIosArm64", libs.androidx.room.compiler)
        add("kspJvm", libs.androidx.room.compiler)
    }
}

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

buildkonfig {
    packageName = "com.posse.tanksrandomizer.common.data.di"
    exposeObjectWithName = "ServerConstants"

    val properties = Properties().apply {
        load(File(projectDir, "serverConstants.properties").reader())
    }

    defaultConfigs {
        buildConfigField(STRING, "APPLICATION_ID", properties.getProperty("appication_id"))
    }
}
