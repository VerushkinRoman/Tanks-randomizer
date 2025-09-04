package com.posse.tanksrandomizer.feature_offline_screen.presentation

import androidx.lifecycle.viewModelScope
import com.posse.tanksrandomizer.common.core.di.Inject
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.ItemStatus
import com.posse.tanksrandomizer.common.domain.models.RepositoryFor
import com.posse.tanksrandomizer.common.domain.repository.CommonTanksRepository
import com.posse.tanksrandomizer.common.domain.utils.Dispatchers
import com.posse.tanksrandomizer.common.presentation.utils.BaseSharedViewModel
import com.posse.tanksrandomizer.feature_offline_screen.domain.repository.OfflineScreenRepository
import com.posse.tanksrandomizer.feature_offline_screen.presentation.models.Numbers
import com.posse.tanksrandomizer.feature_offline_screen.presentation.models.OfflineFilters
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

    override fun obtainEvent(viewEvent: OfflineScreenEvent) {
        when (viewEvent) {
            OfflineScreenEvent.ClearAction -> viewAction = null
            OfflineScreenEvent.GenerateFilterPressed -> generateFilter()
            OfflineScreenEvent.GenerateNumberPressed -> generateNumber()
            OfflineScreenEvent.TrashFilterPressed -> resetFilter()
            OfflineScreenEvent.TrashNumberPressed -> resetQuantity()
            OfflineScreenEvent.SettingsPressed -> toggleSettings()
            OfflineScreenEvent.GoBack -> goBack()
            is OfflineScreenEvent.FilterItemChanged<*> -> handleFilterItemChanged(viewEvent.item)
            is OfflineScreenEvent.QuantityChanged -> changeQuantity(viewEvent.amount)
        }
    }

    private fun <T : ItemStatus<T>> handleFilterItemChanged(item: T) {
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
            viewState = viewState.updateFilters(OfflineFilters())
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

    private fun goBack() {
        viewAction = OfflineScreenAction.ToMainScreen
    }

    private fun makeActionWithViewModelScopeAndSaveState(
        action: suspend () -> Unit
    ) {
        viewModelScope.launch {
            action()
            saveOfflineScreenState(viewState)
        }
    }
}