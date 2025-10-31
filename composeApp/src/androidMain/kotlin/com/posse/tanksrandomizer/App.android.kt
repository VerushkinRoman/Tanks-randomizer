package com.posse.tanksrandomizer

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.content.getSystemService
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.posse.tanksrandomizer.android_mode.compose.AndroidApp
import com.posse.tanksrandomizer.common.compose.components.startWindowMode
import com.posse.tanksrandomizer.common.compose.utils.rotateDevice
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
        createNotificationChannel()
    }
}

class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        addSplashScreen()
    }

    override fun onResume() {
        super.onResume()
        launchApp()
    }
}

private fun AppActivity.launchApp() {
    val repository: SettingsRepository = Inject.instance()
    val windowMode = !repository.getFullScreenMode()
    val canDrawOverlays = Settings.canDrawOverlays(this)

    if (windowMode && canDrawOverlays) {
        startWindowMode(
            context = this,
            startedAsService = true,
        )
    } else {
        rotateDevice()
        setContent {
            AndroidApp(
                startedFromService = false,
                startedAsService = false,
            )
        }
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

fun App.initPlatformSDK() = PlatformSDK.init(
    configuration = PlatformConfiguration(androidContext = applicationContext)
)

private fun App.createNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val notificationChannel = NotificationChannel(
            /* id = */ CHANNEL_ID,
            /* name = */ getString(R.string.notification_channel_name),
            /* importance = */ NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = getString(R.string.notification_channel_discription)
        }

        val notificationManager = getSystemService<NotificationManager>()
        notificationManager?.createNotificationChannel(notificationChannel)
    }
}

const val CHANNEL_ID = "Default"
