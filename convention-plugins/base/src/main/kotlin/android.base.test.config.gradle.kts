import com.android.build.api.dsl.ManagedVirtualDevice
import com.posse.tanksrandomizer.conventionplugins.base.extensions.androidConfig
import com.posse.tanksrandomizer.conventionplugins.base.extensions.libs

androidConfig {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    //https://developer.android.com/studio/test/gradle-managed-devices
    @Suppress("UnstableApiUsage")
    testOptions {
        managedDevices.allDevices {
            maybeCreate<ManagedVirtualDevice>("pixel5").apply {
                device = "Pixel 5"
                apiLevel = libs.versions.targetSdk.get().toInt()
                systemImageSource = "aosp"
            }
        }
    }
}
