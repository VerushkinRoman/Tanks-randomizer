package com.posse.tanksrandomizer.feature_offline_pane.presentation

import androidx.lifecycle.viewModelScope
import com.posse.tanksrandomizer.common.core.di.Inject
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.ItemStatus
import com.posse.tanksrandomizer.common.domain.models.RepositoryType
import com.posse.tanksrandomizer.common.domain.repository.CommonRepository
import com.posse.tanksrandomizer.common.domain.utils.BoxedInt
import com.posse.tanksrandomizer.common.domain.utils.Dispatchers
import com.posse.tanksrandomizer.common.presentation.utils.BaseSharedViewModel
import com.posse.tanksrandomizer.feature_offline_pane.domain.repository.OfflineRepository
import com.posse.tanksrandomizer.feature_offline_pane.presentation.models.OfflineFilters
import com.posse.tanksrandomizer.feature_offline_pane.presentation.models.Numbers
import com.posse.tanksrandomizer.feature_offline_pane.presentation.models.OfflinePaneAction
import com.posse.tanksrandomizer.feature_offline_pane.presentation.models.OfflinePaneEvent
import com.posse.tanksrandomizer.feature_offline_pane.presentation.models.OfflinePaneState
import com.posse.tanksrandomizer.feature_offline_pane.presentation.models.isDefault
import com.posse.tanksrandomizer.feature_offline_pane.presentation.models.reverseSelected
import com.posse.tanksrandomizer.feature_offline_pane.presentation.use_cases.GenerateOfflineFilter
import com.posse.tanksrandomizer.feature_offline_pane.presentation.use_cases.GetOfflinePaneStartState
import com.posse.tanksrandomizer.feature_offline_pane.presentation.use_cases.SaveOfflinePaneState
import kotlinx.coroutines.launch
import kotlin.random.Random

class OfflinePaneViewModel(
    filterRepository: CommonRepository = Inject.instance(tag = RepositoryType.Offline),
    offlineRepository: OfflineRepository = Inject.instance(),
    dispatchers: Dispatchers = Inject.instance(),
) : BaseSharedViewModel<OfflinePaneState, OfflinePaneAction, OfflinePaneEvent>(
    initialState = GetOfflinePaneStartState(
        commonRepository = filterRepository,
        offlineRepository = offlineRepository
    ).invoke()
) {
    private val generateOfflineFilter = GenerateOfflineFilter(dispatchers = dispatchers)
    private val saveOfflinePaneState = SaveOfflinePaneState(
        filterRepository = filterRepository,
        offlineRepository = offlineRepository,
        dispatchers = dispatchers,
    )

    override fun obtainEvent(viewEvent: OfflinePaneEvent) {
        when (viewEvent) {
            OfflinePaneEvent.ClearAction -> viewAction = null
            is OfflinePaneEvent.ExperiencePressed -> viewState.changeItem(viewEvent.experience)
            is OfflinePaneEvent.LevelPressed -> viewState.changeItem(viewEvent.level)
            is OfflinePaneEvent.NationPressed -> viewState.changeItem(viewEvent.nation)
            is OfflinePaneEvent.PinnedPressed -> viewState.changeItem(viewEvent.pinned)
            is OfflinePaneEvent.StatusPressed -> viewState.changeItem(viewEvent.status)
            is OfflinePaneEvent.TankTypePressed -> viewState.changeItem(viewEvent.tankType)
            is OfflinePaneEvent.TypePressed -> viewState.changeItem(viewEvent.type)
            OfflinePaneEvent.GenerateFilterPressed -> generateFilter()
            OfflinePaneEvent.GenerateNumberPressed -> generateNumber()
            OfflinePaneEvent.MinusHundredPressed -> changeQuantity(-100)
            OfflinePaneEvent.MinusTenPressed -> changeQuantity(-10)
            OfflinePaneEvent.MinusPressed -> changeQuantity(-1)
            OfflinePaneEvent.PlusHundredPressed -> changeQuantity(100)
            OfflinePaneEvent.PlusTenPressed -> changeQuantity(10)
            OfflinePaneEvent.PlusPressed -> changeQuantity(1)
            OfflinePaneEvent.TrashFilterPressed -> resetFilter()
            OfflinePaneEvent.TrashNumberPressed -> resetQuantity()
            OfflinePaneEvent.SettingsPressed -> toggleSettings()
        }
    }

    private fun generateFilter() {
        makeActionWithViewModelScopeAndSaveState {
            viewState = viewState.copy(
                offlineFilters = generateOfflineFilter(viewState.offlineFilters),
            )
        }
    }

    private fun generateNumber() {
        viewState = viewState.copy(
            numbers = viewState.numbers.copy(
                generatedQuantity = BoxedInt(Random.nextInt(1, viewState.numbers.quantity + 1)),
            ),
        )
    }

    private fun changeQuantity(add: Int) {
        makeActionWithViewModelScopeAndSaveState {
            viewState = viewState.copy(
                numbers = viewState.numbers.copy(
                    quantity = (viewState.numbers.quantity + add).coerceIn(1, 999),
                ),
            )
        }
    }

    private fun resetFilter() {
        makeActionWithViewModelScopeAndSaveState {
            viewState = if (viewState.offlineFilters.isDefault) {
                viewState.copy(
                    offlineFilters = viewState.offlineFilters.reverseSelected(),
                )
            } else {
                viewState.copy(
                    offlineFilters = OfflineFilters(),
                )
            }
        }
    }

    private fun resetQuantity() {
        makeActionWithViewModelScopeAndSaveState {
            viewState = viewState.copy(
                numbers = Numbers(),
            )
        }
    }

    private fun toggleSettings() {
        viewAction = OfflinePaneAction.ToggleSettings
    }

    @Suppress("UnusedReceiverParameter")
    private fun <T : ItemStatus<T>> OfflinePaneState.changeItem(item: T) {
        makeActionWithViewModelScopeAndSaveState {
            viewState = viewState.copy(
                offlineFilters = viewState.offlineFilters.changeItem(item),
            )
        }
    }

    private fun makeActionWithViewModelScopeAndSaveState(
        action: suspend () -> Unit
    ) {
        viewModelScope.launch {
            action()
            saveOfflinePaneState(viewState)
        }
    }
}