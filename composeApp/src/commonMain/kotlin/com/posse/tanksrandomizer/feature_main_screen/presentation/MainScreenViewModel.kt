package com.posse.tanksrandomizer.feature_main_screen.presentation

import com.posse.tanksrandomizer.common.core.di.Inject
import com.posse.tanksrandomizer.common.domain.repository.AccountRepository
import com.posse.tanksrandomizer.common.domain.utils.Dispatchers
import com.posse.tanksrandomizer.common.domain.utils.Error
import com.posse.tanksrandomizer.common.domain.utils.onError
import com.posse.tanksrandomizer.common.domain.utils.onSuccess
import com.posse.tanksrandomizer.common.presentation.utils.BaseSharedViewModel
import com.posse.tanksrandomizer.feature_main_screen.presentation.models.MainScreenAction
import com.posse.tanksrandomizer.feature_main_screen.presentation.models.MainScreenEvent
import com.posse.tanksrandomizer.feature_main_screen.presentation.models.MainScreenState
import com.posse.tanksrandomizer.feature_main_screen.presentation.use_cases.LogInToAccount

class MainScreenViewModel(
    accountRepository: AccountRepository = Inject.instance(),
    dispatchers: Dispatchers = Inject.instance(),
) : BaseSharedViewModel<MainScreenState, MainScreenAction, MainScreenEvent>(
    initialState = MainScreenState()
) {
    private val logInToAccount = LogInToAccount(
        accountRepository = accountRepository,
        dispatchers = dispatchers,
    )

    override fun obtainEvent(viewEvent: MainScreenEvent) {
        when (viewEvent) {
            MainScreenEvent.ClearAction -> viewAction = null
            MainScreenEvent.LogIn -> login()
            MainScreenEvent.ToOfflineScreen -> toOfflineScreen()
        }
    }

    private fun login() {
        withViewModelScope {
            viewState = viewState.copy(loading = true)

            logInToAccount()
                .onError { error -> showError(error) }
                .onSuccess { uri -> viewAction = MainScreenAction.OpenUri(uri) }

            viewState = viewState.copy(loading = false)
        }
    }

    private fun toOfflineScreen() {
        viewAction = MainScreenAction.ToOfflineScreen
    }

    private fun showError(error: Error) {
        viewAction = MainScreenAction.ShowError(error)
    }
}
