package com.posse.tanksrandomizer.common.presentation.ads

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.posse.tanksrandomizer.common.compose.base_components.LoadingIndicator
import com.posse.tanksrandomizer.common.compose.theme.AppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class RewardAdActivity : ComponentActivity() {
    companion object {
        private const val EXTRA_CALLBACK_ID = "callback_id"

        private val callbacks = mutableMapOf<String, RewardCallback>()

        data class RewardCallback(
            val onRewardReceived: (Int?) -> Unit,
        )

        fun start(
            context: Context,
            onRewardReceived: (Int?) -> Unit,
        ) {
            val callbackId = System.currentTimeMillis().toString()
            val callback = RewardCallback(onRewardReceived)
            callbacks[callbackId] = callback

            Intent(context, RewardAdActivity::class.java).apply {
                putExtra(EXTRA_CALLBACK_ID, callbackId)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            }.let {
                context.startActivity(it)
            }
        }
    }

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private var callbackId: String? = null
    private val adFlow: MutableSharedFlow<Unit> = MutableSharedFlow()
    private var rewardAdManager: RewardAdManager? = null

    init {
        scope.launch {
            adFlow.collect {
                rewardAdManager?.showRewardedAd(context = this@RewardAdActivity)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        callbackId = intent.getStringExtra(EXTRA_CALLBACK_ID)
        initAndShowRewardedAd()
        setContent {
            AppTheme {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primaryContainer)
                ) {
                    LoadingIndicator()
                }
            }
        }
    }

    private fun initAndShowRewardedAd() {
        rewardAdManager = RewardAdManager(
            onAdLoaded = {
                scope.launch {
                    adFlow.emit(Unit)
                }
            },
            onAdFailedToLoad = {},
            onRewarded = { amount ->
                callbacks[callbackId]?.onRewardReceived(amount)
                cleanupAndFinish()
            }
        )

        rewardAdManager?.initADs(this)
    }

    private fun cleanupAndFinish() {
        callbackId?.let { callbacks.remove(it) }
        finish()
    }

    override fun finish() {
        super.finish()
        @Suppress("DEPRECATION")
        overridePendingTransition(0, 0)
    }

    override fun onDestroy() {
        scope.cancel()
        rewardAdManager = null
        callbackId?.let { callbacks.remove(it) }
        super.onDestroy()
    }
}
