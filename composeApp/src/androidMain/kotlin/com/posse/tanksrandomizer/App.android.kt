package com.posse.tanksrandomizer

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.content.getSystemService
import androidx.core.net.toUri
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.posse.tanksrandomizer.AppActivity.Companion.rotateDevice
import com.posse.tanksrandomizer.android_mode.compose.AndroidApp
import com.posse.tanksrandomizer.common.compose.components.startWindowMode
import com.posse.tanksrandomizer.common.core.di.Inject
import com.posse.tanksrandomizer.common.core.platform.PlatformConfiguration
import com.posse.tanksrandomizer.common.core.platform.PlatformSDK
import com.posse.tanksrandomizer.feature_settings_screen.domain.models.ScreenRotation.Auto
import com.posse.tanksrandomizer.feature_settings_screen.domain.models.ScreenRotation.Landscape
import com.posse.tanksrandomizer.feature_settings_screen.domain.models.ScreenRotation.Portrait
import com.posse.tanksrandomizer.feature_settings_screen.domain.repository.SettingsRepository
import com.posse.tanksrandomizer.feature_settings_screen.presentation.interactor.SettingsInteractor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initPlatformSDK()
        createNotificationChannel()
        instance = this
    }

    companion object {
        private var instance: App? = null

        fun openAppSettings() {
            instance?.let { app ->
                Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    "package:${app.packageName}".toUri()
                )
                    .apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK }
                    .also { app.applicationContext.startActivity(it) }
            }
        }

        fun canDrawOverlay(): Boolean {
            return instance?.let { app ->
                Settings.canDrawOverlays(app)
            } ?: false
        }
    }
}

class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        addSplashScreen()
        launchApp()
    }

    override fun onResume() {
        super.onResume()
        instance = this
    }

    override fun onPause() {
        super.onPause()
        instance = null
    }

    companion object {
        private var instance: AppActivity? = null

        fun rotateDevice() {
            instance?.let {
                val repository: SettingsRepository = Inject.instance()
                val rotation = repository.getRotation()

                it.requestedOrientation = when (rotation) {
                    Portrait -> ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
                    Landscape -> ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
                    Auto -> ActivityInfo.SCREEN_ORIENTATION_SENSOR
                }
            }
        }
    }
}

private fun AppActivity.launchApp() {
    val settingsInteractor: SettingsInteractor = Inject.instance()
    val windowMode = !settingsInteractor.fullScreenModeEnabled.value
    val canDrawOverlays = App.canDrawOverlay()

    if (!canDrawOverlays) {
        settingsInteractor.setFullScreenMode(true)
    }

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
