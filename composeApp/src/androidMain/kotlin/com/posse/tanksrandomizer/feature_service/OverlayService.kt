package com.posse.tanksrandomizer.feature_service

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.posse.tanksrandomizer.CHANNEL_ID
import com.posse.tanksrandomizer.common.core.di.Inject
import com.posse.tanksrandomizer.common.domain.model.ButtonSize.ExtraLarge
import com.posse.tanksrandomizer.common.domain.model.ButtonSize.Large
import com.posse.tanksrandomizer.common.domain.model.ButtonSize.Medium
import com.posse.tanksrandomizer.common.domain.model.ButtonSize.Small
import com.posse.tanksrandomizer.common.presentation.interactor.SettingsInteractor
import com.posse.tanksrandomizer.feature_service.compose.components.ChangeSizeButton
import com.posse.tanksrandomizer.feature_service.presentation.FloatingButtonView
import com.posse.tanksrandomizer.feature_service.presentation.MainScreenView
import com.posse.tanksrandomizer.navigation.compose.AndroidApp
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.exitProcess
import kotlin.time.Duration.Companion.seconds

class OverlayService : Service() {
    private val settingsInteractor: SettingsInteractor by lazy { Inject.instance() }

    private val floatingButtonView by lazy { FloatingButtonView(context = this) }
    private val mainScreenView by lazy { MainScreenView(context = this) }

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

    private fun showFloatingButtonOverlay() {
        floatingButtonView.setContent {
            val windowInFullScreen by settingsInteractor.windowInFullScreen.collectAsStateWithLifecycle()
            val size by settingsInteractor.floatingButtonSize.collectAsStateWithLifecycle()
            val opacity by settingsInteractor.floatingButtonOpacity.collectAsStateWithLifecycle()

            val actualSize by animateDpAsState(
                targetValue = when (size) {
                    Small -> ButtonDefaults.MinHeight - 6.dp
                    Medium -> ButtonDefaults.MinHeight
                    Large -> ButtonDefaults.MinHeight + 6.dp
                    ExtraLarge -> ButtonDefaults.MinHeight + 12.dp
                }
            )

            ChangeSizeButton(
                windowInFullScreen = windowInFullScreen,
                modifier = Modifier
                    .size(actualSize)
                    .alpha(opacity)
            )
        }
    }
}