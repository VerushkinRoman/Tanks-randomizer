package com.posse.tanksrandomizer.feature_online_screen.presentation

import androidx.lifecycle.viewModelScope
import com.posse.tanksrandomizer.common.core.di.Inject
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.ItemStatus
import com.posse.tanksrandomizer.common.domain.models.RepositoryType
import com.posse.tanksrandomizer.common.domain.repository.AccountRepository
import com.posse.tanksrandomizer.common.domain.repository.CommonTanksRepository
import com.posse.tanksrandomizer.common.domain.utils.Dispatchers
import com.posse.tanksrandomizer.common.domain.utils.Error
import com.posse.tanksrandomizer.common.domain.utils.Result
import com.posse.tanksrandomizer.common.domain.utils.onError
import com.posse.tanksrandomizer.common.domain.utils.onSuccess
import com.posse.tanksrandomizer.common.presentation.utils.BaseSharedViewModel
import com.posse.tanksrandomizer.feature_online_screen.domain.models.Tank
import com.posse.tanksrandomizer.feature_online_screen.domain.repository.OnlineRepository
import com.posse.tanksrandomizer.feature_online_screen.presentation.models.OnlineFilters
import com.posse.tanksrandomizer.feature_online_screen.presentation.models.OnlineScreenAction
import com.posse.tanksrandomizer.feature_online_screen.presentation.models.OnlineScreenEvent
import com.posse.tanksrandomizer.feature_online_screen.presentation.models.OnlineScreenState
import com.posse.tanksrandomizer.feature_online_screen.presentation.use_cases.FilterTanks
import com.posse.tanksrandomizer.feature_online_screen.presentation.use_cases.GetOnlineScreenStartState
import com.posse.tanksrandomizer.feature_online_screen.presentation.use_cases.RefreshTanks
import com.posse.tanksrandomizer.feature_online_screen.presentation.use_cases.SaveOnlineScreenState
import com.posse.tanksrandomizer.feature_online_screen.presentation.use_cases.UpdateToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class OnlineScreenViewModel(
    filterRepository: CommonTanksRepository = Inject.instance(tag = RepositoryType.Online),
    onlineRepository: OnlineRepository = Inject.instance(),
    accountRepository: AccountRepository = Inject.instance(),
    dispatchers: Dispatchers = Inject.instance(),
    private val filterTanks: FilterTanks = FilterTanks(dispatchers = dispatchers),
) : BaseSharedViewModel<OnlineScreenState, OnlineScreenAction, OnlineScreenEvent>(
    initialState = GetOnlineScreenStartState(
        commonTanksRepository = filterRepository,
        onlineRepository = onlineRepository,
        accountRepository = accountRepository,
        filterTanks = filterTanks,
    ).invoke()
) {
    private val saveOnlineScreenState = SaveOnlineScreenState(
        filterRepository = filterRepository,
        onlineRepository = onlineRepository,
        dispatchers = dispatchers,
    )

    private val refreshTanks = RefreshTanks(
        onlineRepository = onlineRepository,
        dispatchers = dispatchers,
    )

    private val updateToken = UpdateToken(
        accountRepository = accountRepository,
        dispatchers = dispatchers,
    )

    override fun obtainEvent(viewEvent: OnlineScreenEvent) {
        when (viewEvent) {
            OnlineScreenEvent.ClearAction -> viewAction = null
            OnlineScreenEvent.GenerateTankPressed -> generateTank()
            OnlineScreenEvent.RefreshAccountPressed -> refreshAccount()
            OnlineScreenEvent.SettingsPressed -> toggleSettings()
            OnlineScreenEvent.TrashFilterPressed -> resetFilter()
            is OnlineScreenEvent.FilterItemChanged<*> -> handleFilterItemChanged(viewEvent.item)
        }
    }

    private fun <T : ItemStatus<T>> handleFilterItemChanged(item: T) {
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
            val tanksByFilter =
                filterTanks(tanks = viewState.tanksInGarage, filters = OnlineFilters())
            viewState = viewState.updateFilters(OnlineFilters(), tanksByFilter)
        }
    }

    private fun generateTank() {
        if (viewState.numberOfFilteredTanks == 0) return

        makeActionWithViewModelScopeAndSaveState {
            val randomTank = viewState.tanksByFilter.random()
            val filters = viewState.onlineFilters.markRandomForTank(randomTank)

            viewState = viewState.copy(
                generatedTank = randomTank,
                onlineFilters = filters
            )
        }
    }

    private fun refreshAccount() {
        makeActionWithViewModelScopeAndSaveState {
            val tokenDeferred = async { updateToken() }
            val tanksDeferred = async { refreshTanks(viewState.tanksInGarage) }

            val newToken = tokenDeferred.await()

            tanksDeferred.await()
                .onSuccess { newTanks ->
                    handleRefreshSuccess(newTanks)
                    newToken
                        .onSuccess { viewState = viewState.copy(token = it) }
                        .onError { showError(it) }
                }
                .onError {
                    showError(it)
                    if (newToken is Result.Success) {
                        viewState = viewState.copy(token = newToken.data)
                    }
                }
        }
    }

    private suspend fun handleRefreshSuccess(newTanks: List<Tank>) {
        val tanksByFilter = filterTanks(tanks = newTanks, filters = viewState.onlineFilters)
        viewState = viewState.updateTanks(newTanks, tanksByFilter)
    }

    private fun showError(error: Error) {
        viewAction = OnlineScreenAction.ShowError(error)
    }

    private fun makeActionWithViewModelScopeAndSaveState(
        action: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch {
            action()
            saveOnlineScreenState(viewState)
        }
    }
}
