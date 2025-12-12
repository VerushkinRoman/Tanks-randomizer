plugins {
    id("org.jetbrains.kotlin.multiplatform")
}

kotlin {
    jvmToolchain(17)

    androidTarget()

    jvm()

    iosX64()
    iosArm64()
    iosSimulatorArm64()

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
