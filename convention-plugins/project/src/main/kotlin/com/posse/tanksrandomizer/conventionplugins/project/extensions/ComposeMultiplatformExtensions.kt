package com.posse.tanksrandomizer.conventionplugins.project.extensions

import com.posse.tanksrandomizer.conventionplugins.base.extensions.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.compose.ComposeExtension
import org.jetbrains.compose.desktop.DesktopExtension
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

fun Project.composeDesktopApplication(
    mainClass: String,
    packageName: String,
    linuxIconPath: String? = "desktopAppIcons/LinuxIcon.png",
    windowsIconPath: String? = "desktopAppIcons/WindowsIcon.ico",
    macIconPath: String? = "desktopAppIcons/MacosIcon.icns",
    macBundleId: String? = "com.posse.tanksrandomizer",
    version: String = libs.versions.appVersionName.get(),
    targetFormats: List<TargetFormat> = listOf(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
) {
    extensions.getByType<ComposeExtension>().extensions.configure<DesktopExtension> {
        application {
            this.mainClass = mainClass

            nativeDistributions {
                targetFormats(*targetFormats.toTypedArray())
                this.packageName = packageName
                this.packageVersion = version

                linuxIconPath?.let { path ->
                    linux {
                        iconFile.set(project.file(path))
                    }
                }

                windowsIconPath?.let { path ->
                    windows {
                        iconFile.set(project.file(path))
                    }
                }

                macOS {
                    macIconPath?.let { path ->
                        iconFile.set(project.file(path))
                    }
                    macBundleId?.let { id ->
                        bundleID = id
                    }
                }
            }

            buildTypes.release.proguard {
                obfuscate.set(true)
                optimize.set(false)
                configurationFiles.from(
                    project.file("proguard-rules.pro"),
                    project.file("compose-desktop.pro"),
                )
            }
        }
    }
}
