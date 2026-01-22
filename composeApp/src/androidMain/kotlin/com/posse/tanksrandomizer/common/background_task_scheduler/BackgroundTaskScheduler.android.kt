package com.posse.tanksrandomizer.common.background_task_scheduler

import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.posse.tanksrandomizer.common.core.platform.PlatformConfiguration
import java.util.concurrent.TimeUnit

actual class BackgroundTaskScheduler actual constructor(
    private val configuration: PlatformConfiguration
) {
    private val workManager by lazy { WorkManager.getInstance(configuration.androidContext) }
    private val workName = "daily_token_update"

    actual fun scheduleDailyTokenUpdate() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val workRequest = PeriodicWorkRequest.Builder(
            workerClass = TokenUpdateWorker::class.java,
            repeatInterval = 24,
            repeatIntervalTimeUnit = TimeUnit.HOURS
        )
            .setConstraints(constraints)
            .setInitialDelay(duration = 1, timeUnit = TimeUnit.HOURS)
            .build()

        workManager.enqueueUniquePeriodicWork(
            uniqueWorkName = workName,
            existingPeriodicWorkPolicy = ExistingPeriodicWorkPolicy.UPDATE,
            request = workRequest
        )
    }

    actual fun cancelDailyTokenUpdate() {
        workManager.cancelUniqueWork(uniqueWorkName = workName)
    }
}
