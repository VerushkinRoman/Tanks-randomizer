rootProject.name = "Tanks-randomizer"

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
                includeGroupByRegex("android.*")
            }
        }
        gradlePluginPortal()
        mavenCentral()
        maven("https://jogamp.org/deployment/maven")
    }
}

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
                includeGroupByRegex("android.*")
            }
        }
        mavenCentral()
        maven("https://jogamp.org/deployment/maven")
        // IronSource
        maven("https://android-sdk.is.com/")
        // Pangle
        maven("https://artifact.bytedance.com/repository/pangle")
        // Tapjoy
        maven("https://sdk.tapjoy.com/")
        // Mintegral
        maven("https://dl-maven-android.mintegral.com/repository/mbridge_android_sdk_oversea")
        // Chartboost
        maven("https://cboost.jfrog.io/artifactory/chartboost-ads/")
        // AppNext
        maven("https://dl.appnext.com/")
    }
}

include(":composeApp")
include(":androidApp")
