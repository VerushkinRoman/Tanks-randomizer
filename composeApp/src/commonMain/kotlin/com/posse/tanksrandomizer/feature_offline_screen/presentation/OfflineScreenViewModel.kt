package com.posse.tanksrandomizer.feature_offline_screen.presentation

import androidx.lifecycle.viewModelScope
import com.posse.tanksrandomizer.common.core.di.Inject
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.ItemStatus
import com.posse.tanksrandomizer.common.domain.models.RepositoryFor
import com.posse.tanksrandomizer.common.domain.repository.CommonTanksRepository
import com.posse.tanksrandomizer.common.domain.utils.Dispatchers
import com.posse.tanksrandomizer.common.domain.utils.Error
import com.posse.tanksrandomizer.common.domain.utils.onError
import com.posse.tanksrandomizer.common.domain.utils.onSuccess
import com.posse.tanksrandomizer.common.presentation.use_cases.LogInToAccount
import com.posse.tanksrandomizer.common.presentation.utils.BaseSharedViewModel
import com.posse.tanksrandomizer.feature_offline_screen.domain.repository.OfflineScreenRepository
import com.posse.tanksrandomizer.feature_offline_screen.presentation.models.Numbers
import com.posse.tanksrandomizer.feature_offline_screen.presentation.models.OfflineScreenAction
import com.posse.tanksrandomizer.feature_offline_screen.presentation.models.OfflineScreenEvent
import com.posse.tanksrandomizer.feature_offline_screen.presentation.models.OfflineScreenState
import com.posse.tanksrandomizer.feature_offline_screen.presentation.use_cases.GenerateOfflineFilter
import com.posse.tanksrandomizer.feature_offline_screen.presentation.use_cases.GetOfflineScreenStartState
import com.posse.tanksrandomizer.feature_offline_screen.presentation.use_cases.SaveOfflineScreenState
import kotlinx.coroutines.launch

class OfflineScreenViewModel(
    filterRepository: CommonTanksRepository = Inject.instance(tag = RepositoryFor.OfflineScreen),
    offlineScreenRepository: OfflineScreenRepository = Inject.instance(),
    dispatchers: Dispatchers = Inject.instance(),
) : BaseSharedViewModel<OfflineScreenState, OfflineScreenAction, OfflineScreenEvent>(
    initialState = GetOfflineScreenStartState(
        commonTanksRepository = filterRepository,
        offlineScreenRepository = offlineScreenRepository
    ).invoke()
) {
    private val generateOfflineFilter = GenerateOfflineFilter(dispatchers = dispatchers)

    private val saveOfflineScreenState = SaveOfflineScreenState(
        filterRepository = filterRepository,
        offlineScreenRepository = offlineScreenRepository,
        dispatchers = dispatchers,
    )

    private val logInToAccount = LogInToAccount(
        accountRepository = Inject.instance(),
        dispatchers = dispatchers,
    )

    override fun obtainEvent(viewEvent: OfflineScreenEvent) {
        when (viewEvent) {
            OfflineScreenEvent.ClearAction -> viewAction = null
            OfflineScreenEvent.GenerateFilterPressed -> generateFilter()
            OfflineScreenEvent.GenerateNumberPressed -> generateNumber()
            OfflineScreenEvent.CheckAllPressed -> checkAllFilter()
            OfflineScreenEvent.TrashFilterPressed -> resetFilter()
            OfflineScreenEvent.TrashNumberPressed -> resetQuantity()
            OfflineScreenEvent.SettingsPressed -> toggleSettings()
            OfflineScreenEvent.LogInPressed -> logIn()
            OfflineScreenEvent.OnScreenLaunch -> stopLoading()
            is OfflineScreenEvent.FilterItemChanged -> handleFilterItemChanged(viewEvent.item)
            is OfflineScreenEvent.QuantityChanged -> changeQuantity(viewEvent.amount)
        }
    }

    private fun handleFilterItemChanged(item: ItemStatus<*>) {
        makeActionWithViewModelScopeAndSaveState {
            val newFilters = viewState.offlineFilters.changeItem(item)
            viewState = viewState.updateFilters(newFilters)
        }
    }

    private fun generateFilter() {
        makeActionWithViewModelScopeAndSaveState {
            val newFilters = generateOfflineFilter(viewState.offlineFilters)
            viewState = viewState.updateFilters(newFilters)
        }
    }

    private fun generateNumber() {
        viewState = viewState.updateGeneratedQuantity()
    }

    private fun changeQuantity(delta: Int) {
        makeActionWithViewModelScopeAndSaveState {
            val newQuantity = viewState.numbers.quantity + delta
            viewState = viewState.updateQuantity(newQuantity)
        }
    }

    private fun resetFilter() {
        makeActionWithViewModelScopeAndSaveState {
            val newFilters = viewState.offlineFilters.clear()
            viewState = viewState.updateFilters(newFilters)
        }
    }

    private fun checkAllFilter() {
        makeActionWithViewModelScopeAndSaveState {
            val newFilters = viewState.offlineFilters.selectAll()
            viewState = viewState.updateFilters(newFilters)
        }
    }

    private fun resetQuantity() {
        makeActionWithViewModelScopeAndSaveState {
            viewState = viewState.copy(numbers = Numbers())
        }
    }

    private fun toggleSettings() {
        viewAction = OfflineScreenAction.ToggleSettings
    }

    private fun logIn() {
        if (viewState.loading) return

        viewState = viewState.copy(loading = true)

        withViewModelScope {
            logInToAccount()
                .onError { error -> showError(error) }
                .onSuccess { url -> viewAction = OfflineScreenAction.LogIn(url) }
        }
    }

    private fun stopLoading() {
        viewState = viewState.copy(loading = false)
    }

    private fun makeActionWithViewModelScopeAndSaveState(
        action: suspend () -> Unit
    ) {
        viewModelScope.launch {
            action()
            saveOfflineScreenState(viewState)
        }
    }

    private fun showError(error: Error) {
        viewAction = OfflineScreenAction.ShowError(error)
        stopLoading()
    }
}
