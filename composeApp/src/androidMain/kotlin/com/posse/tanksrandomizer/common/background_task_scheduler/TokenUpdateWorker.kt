package com.posse.tanksrandomizer.common.background_task_scheduler

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.posse.tanksrandomizer.common.core.di.Inject
import com.posse.tanksrandomizer.common.data.datasource.TokenManager
import com.posse.tanksrandomizer.common.domain.utils.Dispatchers
import kotlinx.coroutines.withContext

class TokenUpdateWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        return try {
            val tokenManager: TokenManager = Inject.instance()
            val dispatchers: Dispatchers = Inject.instance()
            withContext(dispatchers.io) {
                tokenManager.updateAllTokens(backgroundUpdate = true)
            }
            Result.success()
        } catch (_: Exception) {
            Result.retry()
        }
    }
}
