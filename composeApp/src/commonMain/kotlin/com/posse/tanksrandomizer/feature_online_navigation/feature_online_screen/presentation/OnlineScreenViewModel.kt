package com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.presentation

import com.posse.tanksrandomizer.common.compose.utils.ErrorHandler.isTokenError
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.ItemStatus
import com.posse.tanksrandomizer.common.domain.repository.AccountRepository
import com.posse.tanksrandomizer.common.domain.repository.CommonTanksRepository
import com.posse.tanksrandomizer.common.domain.utils.Dispatchers
import com.posse.tanksrandomizer.common.domain.utils.Error
import com.posse.tanksrandomizer.common.domain.utils.onError
import com.posse.tanksrandomizer.common.domain.utils.onSuccess
import com.posse.tanksrandomizer.common.presentation.utils.BaseSharedViewModel
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.repository.OnlineScreenRepository
import com.posse.tanksrandomizer.feature_online_navigation.common.presentation.interactor.OnlineScreensInteractor
import com.posse.tanksrandomizer.feature_online_navigation.common.presentation.interactor.TokenInteractor
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.presentation.models.OnlineScreenAction
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.presentation.models.OnlineScreenEvent
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.presentation.models.OnlineScreenState
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.presentation.use_cases.FilterTanks
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.presentation.use_cases.GenerateTank
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.presentation.use_cases.GetOnlineScreenStartState
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.presentation.use_cases.LogOut
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.presentation.use_cases.SaveOnlineScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class OnlineScreenViewModel(
    private val screenId: String,
    private val accountId: Int,
    filterRepository: CommonTanksRepository,
    private val onlineScreenRepository: OnlineScreenRepository,
    accountRepository: AccountRepository,
    private val onlineScreensInteractor: OnlineScreensInteractor,
    interactor: TokenInteractor,
    dispatchers: Dispatchers,
    private val filterTanks: FilterTanks = FilterTanks(dispatchers = dispatchers),
) : BaseSharedViewModel<OnlineScreenState, OnlineScreenAction, OnlineScreenEvent>(
    initialState = GetOnlineScreenStartState(
        commonTanksRepository = filterRepository,
        onlineScreenRepository = onlineScreenRepository,
        filterTanks = filterTanks,
    ).invoke(screenId, accountId)
) {
    private val saveOnlineScreenState = SaveOnlineScreenState(
        filterRepository = filterRepository,
        onlineScreenRepository = onlineScreenRepository,
        dispatchers = dispatchers,
    )

    private val generateTank = GenerateTank(
        dispatchers = dispatchers,
    )

    private val logOutFromAccount = LogOut(
        accountRepository = accountRepository,
        onlineScreensInteractor = onlineScreensInteractor,
        onlineScreenRepository = onlineScreenRepository,
        dispatchers = dispatchers,
    )

    init {
        withViewModelScope {
            interactor.tokenStatus.first { statuses ->
                statuses.any { it.accountId == accountId && !it.updating }
            }.let {
                onlineScreenRepository.getLastAccountUpdated(accountId).first()
                    ?.let { updatedTime ->
                        if (Clock.System.now() - updatedTime > 1.days) {
                            refreshAccount(launchedByUser = false)
                        } else {
                            stopLoading()
                        }
                    } ?: refreshAccount(launchedByUser = false)
            }

            launch {
                onlineScreenRepository.getTanksFlowForAccount(accountId).collect { newTanks ->
                    val tanksByFilter = filterTanks(tanks = newTanks, filters = viewState.onlineFilters)
                    viewState = viewState.updateTanks(newTanks, tanksByFilter)
                }
            }

            launch {
                onlineScreenRepository.getLastAccountUpdated(accountId).collect { lastUpdated ->
                    viewState = viewState.copy(
                        lastAccountUpdated = lastUpdated
                    )
                }
            }
        }
    }

    override fun obtainEvent(viewEvent: OnlineScreenEvent) {
        when (viewEvent) {
            OnlineScreenEvent.ClearAction -> viewAction = null
            OnlineScreenEvent.GenerateTankPressed -> generateTank()
            OnlineScreenEvent.RefreshAccountPressed -> refreshAccount(launchedByUser = true)
            OnlineScreenEvent.TrashFilterPressed -> resetFilter()
            OnlineScreenEvent.CheckAllPressed -> checkAllFilter()
            OnlineScreenEvent.LogOutPressed -> showDialog()
            OnlineScreenEvent.ConfirmLogout -> logout(launchedByUser = true)
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
        makeActionWithViewModelScopeAndSaveState {
            val randomTank = generateTank(
                filteredTanks = viewState.tanksByFilter,
                previousTank = viewState.generatedTank
            ) ?: return@makeActionWithViewModelScopeAndSaveState

            val filters = viewState.onlineFilters.markRandomForTank(randomTank)

            viewState = viewState.copy(
                generatedTank = randomTank,
                onlineFilters = filters
            )
        }
    }

    private fun logout(launchedByUser: Boolean) {
        startLoading()

        withViewModelScope {
            logOutFromAccount(screenId = screenId)
                .onSuccess {
                    checkAllFilter()
                    viewAction = OnlineScreenAction.LogOut
                }
                .onError { error ->
                    if (error.isTokenError()) {
                        viewAction = OnlineScreenAction.LogOut
                    } else {
                        showError(error = error, launchedByUser = launchedByUser)
                    }
                }
        }
    }

    private fun refreshAccount(launchedByUser: Boolean) {
        startLoading()

        withViewModelScope {
            onlineScreenRepository
                .refreshTanks(accountId)
                .onSuccess {
                    stopLoading()
                }
                .onError { error ->
                    if (error.isTokenError()) {
                        logout(launchedByUser = launchedByUser)
                    } else {
                        showError(error = error, launchedByUser = launchedByUser)
                    }
                }
        }
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

    private fun showError(error: Error, launchedByUser: Boolean) {
        if (launchedByUser) viewAction = OnlineScreenAction.ShowError(error)
        stopLoading()
    }

    private fun makeActionWithViewModelScopeAndSaveState(
        action: suspend CoroutineScope.() -> Unit
    ): Job {
        return withViewModelScope {
            action()
            saveOnlineScreenState(screenId, viewState)
            stopLoading()
        }
    }
}
