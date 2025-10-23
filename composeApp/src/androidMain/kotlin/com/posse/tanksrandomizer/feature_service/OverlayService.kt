package com.posse.tanksrandomizer.feature_service

import android.app.Notification
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.OnBackPressedDispatcherOwner
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.posse.tanksrandomizer.CHANNEL_ID
import com.posse.tanksrandomizer.android_mode.compose.AndroidApp
import com.posse.tanksrandomizer.common.compose.components.STARTED_AS_SERVICE
import com.posse.tanksrandomizer.common.core.di.Inject
import com.posse.tanksrandomizer.common.presentation.interactor.SettingsInteractor
import com.posse.tanksrandomizer.feature_service.compose.components.ChangeSizeButton
import com.posse.tanksrandomizer.feature_service.presentation.FloatingButtonView
import com.posse.tanksrandomizer.feature_service.presentation.MainScreenView
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.system.exitProcess
import kotlin.time.Duration.Companion.seconds

class OverlayService : LifecycleService() {
    private val settingsInteractor: SettingsInteractor by lazy { Inject.instance() }

    private val floatingButtonView by lazy { FloatingButtonView(context = this) }
    private val mainScreenView by lazy { MainScreenView(context = this) }

    private val asService = MutableStateFlow(false)

    override fun onCreate() {
        super.onCreate()
        showMainOverlay()
        showFloatingButtonOverlay()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        val startedAsService = intent?.getBooleanExtra(STARTED_AS_SERVICE, false) ?: false
        asService.value = startedAsService
        startService()
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return null
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        stopService()
    }

    override fun onDestroy() {
        stopService()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            stopForeground(STOP_FOREGROUND_REMOVE)
        } else {
            @Suppress("DEPRECATION")
            stopForeground(true)
        }

        floatingButtonView.destroy()
        mainScreenView.destroy()
        super.onDestroy()
    }

    private fun startService() {
        val notification: Notification = NotificationCompat
            .Builder(this, CHANNEL_ID)
            .build()

        ServiceCompat.startForeground(
            /* service = */ this,
            /* id = */ 1,
            /* notification = */ notification,
            /* foregroundServiceType = */
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                ServiceInfo.FOREGROUND_SERVICE_TYPE_SPECIAL_USE
            } else {
                0
            }
        )
    }

    fun stopService() {
        stopSelf()
    }

    private fun showMainOverlay() {
        mainScreenView.setContent {
            CompositionLocalProvider(
                LocalOnBackPressedDispatcherOwner.provides(
                    object : OnBackPressedDispatcherOwner {
                        override val onBackPressedDispatcher = OnBackPressedDispatcher()
                        override val lifecycle: Lifecycle
                            get() = this@OverlayService.lifecycle
                    }
                )
            ) {
                val startedAsService by asService.collectAsStateWithLifecycle()

                AndroidApp(
                    startedFromService = true,
                    startedAsService = startedAsService,
                    exitApp = {
                        stopService()

                        @OptIn(DelicateCoroutinesApi::class)
                        GlobalScope.launch {
                            delay(1.seconds)
                            exitProcess(0)
                        }
                    }
                )
            }
        }
    }

    private fun showFloatingButtonOverlay() {
        floatingButtonView.setContent {
            val windowInFullScreen by settingsInteractor.windowInFullScreen.collectAsStateWithLifecycle()
            val size by settingsInteractor.floatingButtonSize.collectAsStateWithLifecycle()
            val opacity by settingsInteractor.floatingButtonOpacity.collectAsStateWithLifecycle()

            val scaleSize by remember(size) { mutableStateOf((ButtonDefaults.MinHeight) * size + 12.dp) }

            ChangeSizeButton(
                windowInFullScreen = windowInFullScreen,
                modifier = Modifier
                    .size(scaleSize)
                    .graphicsLayer { alpha = opacity }
            )
        }
    }
}
