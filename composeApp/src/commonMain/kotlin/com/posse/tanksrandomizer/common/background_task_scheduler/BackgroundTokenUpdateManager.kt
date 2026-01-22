package com.posse.tanksrandomizer.common.background_task_scheduler

import com.posse.tanksrandomizer.common.data.datasource.TokenManager
import com.posse.tanksrandomizer.common.domain.utils.Dispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class BackgroundTokenUpdateManager(
    private val backgroundTaskScheduler: BackgroundTaskScheduler,
    private val tokenManager: TokenManager,
    private val dispatchers: Dispatchers,
) {
    fun initialize() {
        backgroundTaskScheduler.scheduleDailyTokenUpdate()

        CoroutineScope(dispatchers.io).launch {
            tokenManager.updateAllTokens(backgroundUpdate = true)
        }
    }

    fun stop() {
        backgroundTaskScheduler.cancelDailyTokenUpdate()
    }
}
