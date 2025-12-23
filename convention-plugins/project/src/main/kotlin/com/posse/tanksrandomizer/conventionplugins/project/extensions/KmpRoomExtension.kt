package com.posse.tanksrandomizer.conventionplugins.project.extensions

import androidx.room.gradle.RoomExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

fun Project.configureRoom(
    schemaDirectory: String = "$projectDir/schemas"
) {
    extensions.configure<RoomExtension> {
        schemaDirectory(schemaDirectory)
    }
}
