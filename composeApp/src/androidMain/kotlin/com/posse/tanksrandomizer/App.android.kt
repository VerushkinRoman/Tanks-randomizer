package com.posse.tanksrandomizer

import android.app.Application
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.posse.tanksrandomizer.common.compose.utils.DeviceType
import com.posse.tanksrandomizer.common.compose.utils.RotateDirection.Landscape
import com.posse.tanksrandomizer.common.compose.utils.RotateDirection.Portrait
import com.posse.tanksrandomizer.common.core.di.Inject
import com.posse.tanksrandomizer.common.core.platform.PlatformConfiguration
import com.posse.tanksrandomizer.common.core.platform.PlatformSDK
import com.posse.tanksrandomizer.common.domain.repository.SettingsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initPlatformSDK()
    }
}

class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rotateDevice()
        enableEdgeToEdge()
        addSplashScreen()

        setContent { ComposeApp() }
    }
}

private fun AppActivity.addSplashScreen() {
    val splashScreen = installSplashScreen()
    var splashEnabled = true
    splashScreen.setKeepOnScreenCondition { splashEnabled }
    lifecycleScope.launch {
        delay(1000)
        splashEnabled = false
    }
}

private fun AppActivity.rotateDevice() {
    val repository: SettingsRepository = Inject.instance()
    val rotation = repository.getRotation()
    val autorotate = repository.getAutorotate()
    requestedOrientation = if (autorotate) {
        ActivityInfo.SCREEN_ORIENTATION_SENSOR
    } else {
        when (rotation) {
            Portrait -> ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
            Landscape -> ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
        }
    }
}

fun App.initPlatformSDK() = PlatformSDK.init(
    configuration = PlatformConfiguration(androidContext = applicationContext)
)

internal actual val deviceType: DeviceType
    get() = DeviceType.Android