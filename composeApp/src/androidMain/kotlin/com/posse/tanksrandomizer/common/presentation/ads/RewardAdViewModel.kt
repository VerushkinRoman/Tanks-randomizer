package com.posse.tanksrandomizer.common.presentation.ads

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.posse.tanksrandomizer.common.domain.repository.ADRepository
import com.posse.tanksrandomizer.common.presentation.ads.models.RewardAdAction
import com.posse.tanksrandomizer.common.presentation.ads.models.RewardAdEvent
import com.posse.tanksrandomizer.common.presentation.ads.models.RewardAdState
import com.posse.tanksrandomizer.common.presentation.utils.BaseSharedViewModel
import com.posse.tanksrandomizer.feature_settings_screen.presentation.interactor.SettingsInteractor
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.combine
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.Instant

class RewardAdViewModel(
    private val appAsService: Boolean,
    private val adRepository: ADRepository,
    private val settingsInteractor: SettingsInteractor,
) : BaseSharedViewModel<RewardAdState, RewardAdAction, RewardAdEvent>(
    initialState = RewardAdState()
) {
    private val rewardAdManager = RewardAdManager(
        onAdLoaded = {
            enableShowAdButton()
        },
        onAdFailedToLoad = {
            disableShowAdButton()
        },
        onRewarded = { amount ->
            adRepository.setLastAdWatchTime(Clock.System.now().epochSeconds)
            adRepository.setAdCount(viewState.rewardCount + amount)
            if (appAsService) settingsInteractor.setWindowInFullScreen(true)
        }
    )

    init {
        if (appAsService) enableShowAdButton()

        withViewModelScope {
            adRepository.getAdCount()
                .combine(settingsInteractor.adsDisabled) { count, disabled -> count to disabled }
                .collect { (count, disabled) ->
                    cancelLoadingAd()

                    if (disabled) {
                        viewState = viewState.copy(adAvailable = false)
                        return@collect
                    }

                    val lastWatchDate = adRepository.getLastAdWatchTime()?.let { time ->
                        Instant.fromEpochSeconds(time).toLocalDateTime(TimeZone.UTC).date
                    }

                    val today = Clock.System.now().toLocalDateTime(TimeZone.UTC).date

                    if (lastWatchDate == today) {
                        viewState = viewState.copy(adAvailable = count < MAX_ADS_PER_DAY)
                    } else {
                        adRepository.setAdCount(0)
                        viewState = viewState.copy(adAvailable = true)
                    }

                    viewState = viewState.copy(rewardCount = count)

                    if (!appAsService) loadRewardedAd()
                }
        }
    }

    override fun obtainEvent(viewEvent: RewardAdEvent) {
        when (viewEvent) {
            is RewardAdEvent.OnScreenStart -> initADs(viewEvent.context)
            is RewardAdEvent.ShowRewardedAd -> showRewardedAd(viewEvent.context)
        }
    }

    private fun initADs(context: Context) {
        rewardAdManager.initADs(context)
    }

    private fun showRewardedAd(context: Context) {
        disableShowAdButton()
        rewardAdManager.showRewardedAd(
            context = context,
            onRewardActivityStart = { settingsInteractor.setWindowInFullScreen(false) }
        )
    }

    private fun loadRewardedAd() {
        disableShowAdButton()
        if (viewState.adAvailable) {
            rewardAdManager.loadRewardedAd()
        }
    }

    private fun cancelLoadingAd() {
        disableShowAdButton()
        rewardAdManager.cancelLoadingAd()
    }

    private fun enableShowAdButton() {
        viewState = viewState.copy(adLoaded = true)
    }

    private fun disableShowAdButton() {
        if (appAsService) return
        viewState = viewState.copy(adLoaded = false)
    }

    override fun onCleared() {
        viewModelScope.cancel()
        rewardAdManager.onCleared()
        super.onCleared()
    }

    companion object {
        const val MAX_ADS_PER_DAY = 3
    }
}
