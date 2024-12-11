package com.posse.tanksrandomizer.feature_service

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.AndroidUiDispatcher
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.posse.tanksrandomizer.CHANNEL_ID
import com.posse.tanksrandomizer.common.core.di.Inject
import com.posse.tanksrandomizer.common.presentation.interactor.ScreenSettingsInteractor
import com.posse.tanksrandomizer.feature_service.compose.components.ChangeSizeButton
import com.posse.tanksrandomizer.feature_service.presentation.FloatingButtonView
import com.posse.tanksrandomizer.feature_service.presentation.MainScreenView
import com.posse.tanksrandomizer.navigation.compose.AndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelChildren

class OverlayService : Service() {
    private val screenSettingsInteractor: ScreenSettingsInteractor by lazy { Inject.instance() }
    private val scope = CoroutineScope(AndroidUiDispatcher.CurrentThread + SupervisorJob())

    private val floatingButtonView by lazy {
        FloatingButtonView(
            context = this,
            scope = scope,
        )
    }

    private val mainScreenView by lazy {
        MainScreenView(
            context = this,
            scope = scope,
            onLayoutChange = {
                floatingButtonView.updateLayoutParams()
            }
        )
    }

    override fun onCreate() {
        super.onCreate()
        showMainOverlay()
        showFloatingButtonOverlay()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        startService()
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent): IBinder? = null

    override fun onTaskRemoved(rootIntent: Intent?) {
        stopService()
    }

    override fun onDestroy() {
        stopService()
        scope.cancel()
        scope.coroutineContext.cancelChildren()

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
            AndroidApp(
                startedFromService = true,
                exitApp = { onTaskRemoved(null) }
            )
        }
        mainScreenView.update()
    }

    private fun showFloatingButtonOverlay() {
        floatingButtonView.setContent {
            val windowInFullScreen by screenSettingsInteractor.windowInFullScreen.collectAsStateWithLifecycle()
            ChangeSizeButton(windowInFullScreen = windowInFullScreen)
        }
        floatingButtonView.update()
    }
}