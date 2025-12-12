import com.posse.tanksrandomizer.conventionplugins.base.extensions.androidConfig
import com.posse.tanksrandomizer.conventionplugins.base.extensions.kotlinJvmCompilerOptions
import com.posse.tanksrandomizer.conventionplugins.base.extensions.libs
import com.posse.tanksrandomizer.conventionplugins.base.extensions.projectJavaVersion
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

androidConfig {
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
    }

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

kotlinJvmCompilerOptions {
    jvmTarget.set(JvmTarget.fromTarget(projectJavaVersion.toString()))
    freeCompilerArgs.add("-Xjdk-release=${projectJavaVersion}")
}
