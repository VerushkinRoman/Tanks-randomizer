package com.posse.tanksrandomizer.common.presentation.ads

import android.content.Context
import com.posse.tanksrandomizer.common.compose.utils.findActivity
import com.yandex.mobile.ads.common.AdError
import com.yandex.mobile.ads.common.AdRequestConfiguration
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import com.yandex.mobile.ads.rewarded.Reward
import com.yandex.mobile.ads.rewarded.RewardedAd
import com.yandex.mobile.ads.rewarded.RewardedAdEventListener
import com.yandex.mobile.ads.rewarded.RewardedAdLoadListener
import com.yandex.mobile.ads.rewarded.RewardedAdLoader

class RewardAdManager(
    private val onAdLoaded: () -> Unit,
    private val onAdFailedToLoad: () -> Unit,
    private val onRewarded: (Int) -> Unit,
) {
    private var rewardedAdLoader: RewardedAdLoader? = null
    private var rewardedAd: RewardedAd? = null
    private val rewardedAdEventListener = ManagerRewardedAdEventListener()

    fun initADs(context: Context) {
        setAdLoader(context)
        loadRewardedAd()
    }

    fun loadRewardedAd() {
        rewardedAdLoader?.loadAd(createAdRequestConfiguration())
    }

    fun showRewardedAd(
        context: Context,
        onRewardActivityStart: (() -> Unit)? = null,
    ) {
        context.findActivity()?.let { activity ->
            rewardedAd?.apply {
                setAdEventListener(rewardedAdEventListener)
                show(activity)
            }
        } ?: run {
            onRewardActivityStart?.invoke()

            RewardAdActivity.start(
                context = context,
                onRewardReceived = { reward ->
                    reward?.let { onRewarded(it) }
                },
            )
        }
    }

    fun cancelLoadingAd() {
        rewardedAdLoader?.cancelLoading()
        rewardedAd = null
    }

    fun onCleared() {
        cancelLoadingAd()
        destroyRewardedAd()
        rewardedAdLoader?.setAdLoadListener(null)
        rewardedAdLoader = null
    }

    private fun setAdLoader(context: Context) {
        if (rewardedAdLoader != null) return

        rewardedAdLoader = RewardedAdLoader(context).apply {
            setAdLoadListener(
                object : RewardedAdLoadListener {
                    override fun onAdLoaded(rewarded: RewardedAd) {
                        rewardedAd = rewarded
                        onAdLoaded()
                    }

                    override fun onAdFailedToLoad(error: AdRequestError) {
                        onAdFailedToLoad()
                        destroyRewardedAd()
                        loadRewardedAd()
                    }
                }
            )
        }
    }

    private fun createAdRequestConfiguration(): AdRequestConfiguration =
        AdRequestConfiguration.Builder("R-M-18318129-2").build()

    private fun destroyRewardedAd() {
        rewardedAd?.setAdEventListener(null)
        rewardedAd = null
    }

    private inner class ManagerRewardedAdEventListener : RewardedAdEventListener {
        override fun onAdShown() = Unit
        override fun onAdClicked() = Unit
        override fun onAdImpression(impressionData: ImpressionData?) = Unit

        override fun onAdFailedToShow(adError: AdError) {
            destroyRewardedAd()
            loadRewardedAd()
        }

        override fun onAdDismissed() {
            destroyRewardedAd()
            loadRewardedAd()
        }

        override fun onRewarded(reward: Reward) {
            destroyRewardedAd()
            onRewarded(reward.amount)
        }
    }
}
