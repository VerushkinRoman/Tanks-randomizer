plugins {
    alias(libs.plugins.multiplatform) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.compose) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlinx.serialization) apply false
    alias(libs.plugins.buildkonfig) apply false
    alias(libs.plugins.realmDB) apply false
    alias(libs.plugins.google.gms.google.services) apply false
    alias(libs.plugins.google.firebase.crashlytics) apply false
    id("project.plugin")
}
