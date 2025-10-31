package com.posse.tanksrandomizer.feature_online_screen.presentation

import com.posse.tanksrandomizer.common.core.di.Inject
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.ItemStatus
import com.posse.tanksrandomizer.common.domain.models.RepositoryFor
import com.posse.tanksrandomizer.common.domain.repository.AccountRepository
import com.posse.tanksrandomizer.common.domain.repository.CommonTanksRepository
import com.posse.tanksrandomizer.common.domain.utils.Dispatchers
import com.posse.tanksrandomizer.common.domain.utils.Error
import com.posse.tanksrandomizer.common.domain.utils.NetworkError.Companion.isTokenError
import com.posse.tanksrandomizer.common.domain.utils.onError
import com.posse.tanksrandomizer.common.domain.utils.onSuccess
import com.posse.tanksrandomizer.common.presentation.utils.BaseSharedViewModel
import com.posse.tanksrandomizer.feature_online_screen.domain.models.Tank
import com.posse.tanksrandomizer.feature_online_screen.domain.repository.OnlineScreenRepository
import com.posse.tanksrandomizer.feature_online_screen.presentation.models.OnlineScreenAction
import com.posse.tanksrandomizer.feature_online_screen.presentation.models.OnlineScreenEvent
import com.posse.tanksrandomizer.feature_online_screen.presentation.models.OnlineScreenState
import com.posse.tanksrandomizer.feature_online_screen.presentation.use_cases.FilterTanks
import com.posse.tanksrandomizer.feature_online_screen.presentation.use_cases.GetOnlineScreenStartState
import com.posse.tanksrandomizer.feature_online_screen.presentation.use_cases.LogOut
import com.posse.tanksrandomizer.feature_online_screen.presentation.use_cases.RefreshTanks
import com.posse.tanksrandomizer.feature_online_screen.presentation.use_cases.SaveOnlineScreenState
import com.posse.tanksrandomizer.feature_online_screen.presentation.use_cases.UpdateToken
import kotlinx.coroutines.CoroutineScope
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class OnlineScreenViewModel(
    filterRepository: CommonTanksRepository = Inject.instance(tag = RepositoryFor.OnlineScreen),
    onlineScreenRepository: OnlineScreenRepository = Inject.instance(),
    accountRepository: AccountRepository = Inject.instance(),
    dispatchers: Dispatchers = Inject.instance(),
    private val filterTanks: FilterTanks = FilterTanks(dispatchers = dispatchers),
) : BaseSharedViewModel<OnlineScreenState, OnlineScreenAction, OnlineScreenEvent>(
    initialState = GetOnlineScreenStartState(
        commonTanksRepository = filterRepository,
        onlineScreenRepository = onlineScreenRepository,
        filterTanks = filterTanks,
    ).invoke()
) {
    private val saveOnlineScreenState = SaveOnlineScreenState(
        filterRepository = filterRepository,
        onlineScreenRepository = onlineScreenRepository,
        dispatchers = dispatchers,
    )

    private val refreshTanks = RefreshTanks(
        onlineScreenRepository = onlineScreenRepository,
        dispatchers = dispatchers,
    )

    private val updateToken = UpdateToken(
        accountRepository = accountRepository,
        dispatchers = dispatchers,
    )

    private val logOutFromAccount = LogOut(
        accountRepository = accountRepository,
        onlineScreenRepository = onlineScreenRepository,
        dispatchers = dispatchers,
    )

    init {
        withViewModelScope {
            refreshAccount()
        }
    }

    override fun obtainEvent(viewEvent: OnlineScreenEvent) {
        when (viewEvent) {
            OnlineScreenEvent.ClearAction -> viewAction = null
            OnlineScreenEvent.GenerateTankPressed -> generateTank()
            OnlineScreenEvent.RefreshAccountPressed -> refreshAccount()
            OnlineScreenEvent.SettingsPressed -> toggleSettings()
            OnlineScreenEvent.TrashFilterPressed -> resetFilter()
            OnlineScreenEvent.CheckAllPressed -> checkAllFilter()
            OnlineScreenEvent.LogOutPressed -> showDialog()
            OnlineScreenEvent.ConfirmLogout -> logout()
            OnlineScreenEvent.DismissLogout -> hideDialog()
            is OnlineScreenEvent.FilterItemChanged -> handleFilterItemChanged(viewEvent.item)
        }
    }

    private fun handleFilterItemChanged(item: ItemStatus<*>) {
        makeActionWithViewModelScopeAndSaveState {
            val newFilters = viewState.onlineFilters.changeItem(item)
            val tanksByFilter = filterTanks(tanks = viewState.tanksInGarage, filters = newFilters)
            viewState = viewState.updateFilters(newFilters, tanksByFilter)
        }
    }

    private fun toggleSettings() {
        viewAction = OnlineScreenAction.ToggleSettings
    }

    private fun resetFilter() {
        makeActionWithViewModelScopeAndSaveState {
            val newFilters = viewState.onlineFilters.clear()
            val tanksByFilter = filterTanks(tanks = viewState.tanksInGarage, filters = newFilters)
            viewState = viewState.updateFilters(newFilters, tanksByFilter)
        }
    }

    private fun checkAllFilter() {
        makeActionWithViewModelScopeAndSaveState {
            val newFilters = viewState.onlineFilters.selectAll()
            val tanksByFilter = filterTanks(tanks = viewState.tanksInGarage, filters = newFilters)
            viewState = viewState.updateFilters(newFilters, tanksByFilter)
        }
    }

    private fun generateTank() {
        if (viewState.tanksByFilter.isEmpty()) return

        makeActionWithViewModelScopeAndSaveState {
            val randomTank = viewState.tanksByFilter.random()
            val filters = viewState.onlineFilters.markRandomForTank(randomTank)

            viewState = viewState.copy(
                generatedTank = randomTank,
                onlineFilters = filters
            )
        }
    }

    private fun logout() {
        startLoading()

        withViewModelScope {
            logOutFromAccount()
                .onSuccess {
                    checkAllFilter()
                    viewAction = OnlineScreenAction.LogOut
                }
                .onError { error ->
                    if (error.isTokenError()) {
                        viewAction = OnlineScreenAction.LogOut
                    } else {
                        showError(error)
                    }
                }
        }
    }

    private fun refreshAccount() {
        startLoading()

        makeActionWithViewModelScopeAndSaveState {
            updateToken()
                .onError { error ->
                    if (error.isTokenError()) {
                        logout()
                    } else {
                        showError(error)
                    }
                    return@makeActionWithViewModelScopeAndSaveState
                }

            refreshTanks(viewState.tanksInGarage)
                .onSuccess { newTanks ->
                    handleRefreshSuccess(newTanks)
                }
                .onError { error ->
                    if (error.isTokenError()) {
                        logout()
                    } else {
                        showError(error)
                    }
                }
        }
    }

    private suspend fun handleRefreshSuccess(newTanks: List<Tank>) {
        val tanksByFilter = filterTanks(tanks = newTanks, filters = viewState.onlineFilters)
        viewState = viewState.updateTanks(newTanks, tanksByFilter)
    }

    private fun showDialog() {
        viewState = viewState.copy(logoutDialogVisible = true)
    }

    private fun hideDialog() {
        viewState = viewState.copy(logoutDialogVisible = false)
    }

    private fun stopLoading() {
        viewState = viewState.copy(loading = false)
    }

    private fun startLoading() {
        viewState = viewState.copy(loading = true)
    }

    private fun showError(error: Error) {
        viewAction = OnlineScreenAction.ShowError(error)
        stopLoading()
    }

    private fun makeActionWithViewModelScopeAndSaveState(
        action: suspend CoroutineScope.() -> Unit
    ) {
        withViewModelScope {
            action()
            saveOnlineScreenState(viewState)
            stopLoading()
        }
    }
}
