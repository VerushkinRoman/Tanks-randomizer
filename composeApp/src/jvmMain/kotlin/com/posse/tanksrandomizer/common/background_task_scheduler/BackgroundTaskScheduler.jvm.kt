package com.posse.tanksrandomizer.common.background_task_scheduler

import com.posse.tanksrandomizer.common.core.di.Inject
import com.posse.tanksrandomizer.common.core.platform.PlatformConfiguration
import com.posse.tanksrandomizer.common.data.datasource.TokenManager
import com.posse.tanksrandomizer.common.domain.utils.Dispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

actual class BackgroundTaskScheduler actual constructor(
    configuration: PlatformConfiguration
) {
    private var scheduler: ScheduledExecutorService? = null

    actual fun scheduleDailyTokenUpdate() {
        cancelDailyTokenUpdate()

        scheduler = Executors.newScheduledThreadPool(/* corePoolSize = */ 1)

        scheduler?.scheduleAtFixedRate(
            /* command = */ { runUpdateTask() },
            /* initialDelay = */ 1,
            /* period = */ 24,
            /* unit = */ TimeUnit.HOURS
        )
    }

    actual fun cancelDailyTokenUpdate() {
        scheduler?.shutdown()
        scheduler = null
    }

    private fun runUpdateTask() {
        val tokenManager: TokenManager = Inject.instance()
        val dispatchers: Dispatchers = Inject.instance()
        CoroutineScope(dispatchers.io).launch {
            tokenManager.updateAllTokens(backgroundUpdate = true)
        }
    }
}
