package com.posse.tanksrandomizer.feature_offline_pane.presentation

import androidx.lifecycle.viewModelScope
import com.posse.tanksrandomizer.common.core.di.Inject
import com.posse.tanksrandomizer.common.domain.models.FilterObjects.ItemStatus
import com.posse.tanksrandomizer.common.domain.repository.FilterRepository
import com.posse.tanksrandomizer.common.domain.utils.BoxedInt
import com.posse.tanksrandomizer.common.domain.utils.Dispatchers
import com.posse.tanksrandomizer.common.presentation.utils.BaseSharedViewModel
import com.posse.tanksrandomizer.feature_offline_pane.domain.repository.OfflineRepository
import com.posse.tanksrandomizer.feature_offline_pane.presentation.models.Filters
import com.posse.tanksrandomizer.feature_offline_pane.presentation.models.Numbers
import com.posse.tanksrandomizer.feature_offline_pane.presentation.models.OfflineScreenAction
import com.posse.tanksrandomizer.feature_offline_pane.presentation.models.OfflineScreenEvent
import com.posse.tanksrandomizer.feature_offline_pane.presentation.models.OfflineScreenState
import com.posse.tanksrandomizer.feature_offline_pane.presentation.models.isDefault
import com.posse.tanksrandomizer.feature_offline_pane.presentation.models.reverseSelected
import com.posse.tanksrandomizer.feature_offline_pane.presentation.use_cases.GenerateFilter
import com.posse.tanksrandomizer.feature_offline_pane.presentation.use_cases.GetOfflineScreenStartState
import com.posse.tanksrandomizer.feature_offline_pane.presentation.use_cases.SaveOfflineScreenState
import kotlinx.coroutines.launch
import kotlin.random.Random

class OfflineScreenViewModel(
    filterRepository: FilterRepository = Inject.instance(),
    offlineRepository: OfflineRepository = Inject.instance(),
    dispatchers: Dispatchers = Inject.instance(),
) : BaseSharedViewModel<OfflineScreenState, OfflineScreenAction, OfflineScreenEvent>(
    initialState = GetOfflineScreenStartState(
        filterRepository = filterRepository,
        offlineRepository = offlineRepository
    ).invoke()
) {
    private val generateFilterUseCase = GenerateFilter(
        dispatchers = dispatchers
    )
    private val saveOfflineScreenState = SaveOfflineScreenState(
        filterRepository = filterRepository,
        offlineRepository = offlineRepository,
        dispatchers = dispatchers,
    )

    override fun obtainEvent(viewEvent: OfflineScreenEvent) {
        when (viewEvent) {
            OfflineScreenEvent.ClearAction -> viewAction = null
            is OfflineScreenEvent.ExperiencePressed -> viewState.changeItem(viewEvent.experience)
            is OfflineScreenEvent.LevelPressed -> viewState.changeItem(viewEvent.level)
            is OfflineScreenEvent.NationPressed -> viewState.changeItem(viewEvent.nation)
            is OfflineScreenEvent.PinnedPressed -> viewState.changeItem(viewEvent.pinned)
            is OfflineScreenEvent.StatusPressed -> viewState.changeItem(viewEvent.status)
            is OfflineScreenEvent.TankTypePressed -> viewState.changeItem(viewEvent.tankType)
            is OfflineScreenEvent.TypePressed -> viewState.changeItem(viewEvent.type)
            OfflineScreenEvent.GenerateFilterPressed -> generateFilter()
            OfflineScreenEvent.GenerateNumberPressed -> generateNumber()
            OfflineScreenEvent.MinusHundredPressed -> changeQuantity(-100)
            OfflineScreenEvent.MinusTenPressed -> changeQuantity(-10)
            OfflineScreenEvent.MinusPressed -> changeQuantity(-1)
            OfflineScreenEvent.PlusHundredPressed -> changeQuantity(100)
            OfflineScreenEvent.PlusTenPressed -> changeQuantity(10)
            OfflineScreenEvent.PlusPressed -> changeQuantity(1)
            OfflineScreenEvent.TrashFilterPressed -> resetFilter()
            OfflineScreenEvent.TrashNumberPressed -> resetQuantity()
            OfflineScreenEvent.SettingsPressed -> toggleSettings()
        }
    }

    private fun generateFilter() {
        makeActionWithViewModelScopeAndSaveState {
            viewState = viewState.copy(
                filters = generateFilterUseCase(viewState.filters),
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
            viewState = if (viewState.filters.isDefault) {
                viewState.copy(
                    filters = viewState.filters.reverseSelected(),
                )
            } else {
                viewState.copy(
                    filters = Filters(),
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

    @Suppress("UnusedReceiverParameter")
    private fun <T : ItemStatus<T>> OfflineScreenState.changeItem(item: T) {
        makeActionWithViewModelScopeAndSaveState {
            viewState = viewState.copy(
                filters = viewState.filters.changeItem(item),
            )
        }
    }

    private fun toggleSettings() {
        viewAction = OfflineScreenAction.ToggleSettings
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