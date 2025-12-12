import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "com.posse.tanksrandomizer.conventionplugins"

dependencies {
    implementation(libs.gradleplugin.android)
    implementation(libs.gradleplugin.compose)
    implementation(libs.gradleplugin.composeCompiler)
    implementation(libs.gradleplugin.kotlin)
    implementation(libs.gradleplugin.buildkonfig)
    implementation(libs.gradleplugin.buildkonfig.kompiler)
    // Workaround for version catalog working inside precompiled scripts
    // Issue - https://github.com/gradle/gradle/issues/15383
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}

private val projectJavaVersion: JavaVersion = JavaVersion.toVersion(libs.versions.java.get())

java {
    sourceCompatibility = projectJavaVersion
    targetCompatibility = projectJavaVersion
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions.jvmTarget.set(JvmTarget.fromTarget(projectJavaVersion.toString()))
}
