package com.posse.tanksrandomizer.feature_online_navigation.feature_main_screen.presentation

import com.posse.tanksrandomizer.common.domain.repository.AccountRepository
import com.posse.tanksrandomizer.common.domain.utils.Error
import com.posse.tanksrandomizer.common.domain.utils.onError
import com.posse.tanksrandomizer.common.domain.utils.onSuccess
import com.posse.tanksrandomizer.common.presentation.utils.BaseSharedViewModel
import com.posse.tanksrandomizer.feature_online_navigation.feature_main_screen.presentation.models.MainScreenAction
import com.posse.tanksrandomizer.feature_online_navigation.feature_main_screen.presentation.models.MainScreenEvent
import com.posse.tanksrandomizer.feature_online_navigation.feature_main_screen.presentation.models.MainScreenState

class MainScreenViewModel(
    private val accountRepository: AccountRepository,
) : BaseSharedViewModel<MainScreenState, MainScreenAction, MainScreenEvent>(
    initialState = MainScreenState()
) {
    override fun obtainEvent(viewEvent: MainScreenEvent) {
        when (viewEvent) {
            MainScreenEvent.ClearAction -> viewAction = null
            MainScreenEvent.LogIn -> logIn()
            MainScreenEvent.OnScreenLaunch -> stopLoading()
        }
    }

    private fun logIn() {
        if (viewState.loading) return

        viewState = viewState.copy(loading = true)

        withViewModelScope {
            accountRepository.logIn()
                .onError { error -> showError(error) }
                .onSuccess { url -> viewAction = MainScreenAction.OpenUrl(url) }
        }
    }

    private fun stopLoading() {
        viewState = viewState.copy(loading = false)
    }

    private fun showError(error: Error) {
        viewAction = MainScreenAction.ShowError(error)
        stopLoading()
    }
}
