package com.posse.tanksrandomizer.conventionplugins.project.extensions

import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import com.codingfeline.buildkonfig.gradle.BuildKonfigExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import java.io.File
import java.util.Properties

fun Project.configureBuildKonfig(
    packageName: String,
    buildConfigFields: List<Pair<String, String>>,
) {
    extensions.configure<BuildKonfigExtension> {
        this.packageName = packageName
        exposeObjectWithName = "ServerConstants"

        val properties = Properties().apply {
            load(File(projectDir, "serverConstants.properties").reader())
        }

        defaultConfigs {
            buildConfigFields.forEach { field ->
                buildConfigField(STRING, field.first, properties.getProperty(field.second))
            }
        }
    }
}