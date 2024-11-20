package com.posse.tanksrandomizer.feature_main_screen.presentation

import androidx.lifecycle.viewModelScope
import com.posse.tanksrandomizer.common.core.di.Inject
import com.posse.tanksrandomizer.common.domain.model.FilterObjects.ItemStatus
import com.posse.tanksrandomizer.common.domain.utils.BoxedInt
import com.posse.tanksrandomizer.common.domain.utils.Dispatchers
import com.posse.tanksrandomizer.common.presentation.utils.BaseSharedViewModel
import com.posse.tanksrandomizer.feature_main_screen.domain.repository.MainRepository
import com.posse.tanksrandomizer.feature_main_screen.presentation.model.Filters
import com.posse.tanksrandomizer.feature_main_screen.presentation.model.MainAction
import com.posse.tanksrandomizer.feature_main_screen.presentation.model.MainEvent
import com.posse.tanksrandomizer.feature_main_screen.presentation.model.MainState
import com.posse.tanksrandomizer.feature_main_screen.presentation.model.Numbers
import com.posse.tanksrandomizer.feature_main_screen.presentation.model.isDefault
import com.posse.tanksrandomizer.feature_main_screen.presentation.model.reverseSelected
import com.posse.tanksrandomizer.feature_main_screen.presentation.use_cases.GenerateFilter
import com.posse.tanksrandomizer.feature_main_screen.presentation.use_cases.GetMainStartState
import com.posse.tanksrandomizer.feature_main_screen.presentation.use_cases.SaveMainState
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainViewModel(
    repository: MainRepository = Inject.instance(),
    dispatchers: Dispatchers = Inject.instance(),
) : BaseSharedViewModel<MainState, MainAction, MainEvent>(
    initialState = GetMainStartState(repository).invoke()
) {
    private val generateFilterUseCase = GenerateFilter(
        dispatchers = dispatchers
    )
    private val saveMainState = SaveMainState(
        repository = repository,
        dispatchers = dispatchers,
    )

    override fun obtainEvent(viewEvent: MainEvent) {
        when (viewEvent) {
            MainEvent.ClearAction -> viewAction = null
            is MainEvent.ExperiencePressed -> viewState.changeItem(viewEvent.experience)
            is MainEvent.LevelPressed -> viewState.changeItem(viewEvent.level)
            is MainEvent.NationPressed -> viewState.changeItem(viewEvent.nation)
            is MainEvent.PinnedPressed -> viewState.changeItem(viewEvent.pinned)
            is MainEvent.StatusPressed -> viewState.changeItem(viewEvent.status)
            is MainEvent.TankTypePressed -> viewState.changeItem(viewEvent.tankType)
            is MainEvent.TypePressed -> viewState.changeItem(viewEvent.type)
            MainEvent.GenerateFilterPressed -> generateFilter()
            MainEvent.GenerateNumberPressed -> generateNumber()
            MainEvent.MinusHundredPressed -> changeQuantity(-100)
            MainEvent.MinusTenPressed -> changeQuantity(-10)
            MainEvent.MinusPressed -> changeQuantity(-1)
            MainEvent.PlusHundredPressed -> changeQuantity(100)
            MainEvent.PlusTenPressed -> changeQuantity(10)
            MainEvent.PlusPressed -> changeQuantity(1)
            MainEvent.TrashFilterPressed -> resetFilter()
            MainEvent.TrashNumberPressed -> resetQuantity()
            MainEvent.SettingsPressed -> openSettings()
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
    private fun <T : ItemStatus<T>> MainState.changeItem(item: T) {
        makeActionWithViewModelScopeAndSaveState {
            viewState = viewState.copy(
                filters = viewState.filters.changeItem(item),
            )
        }
    }

    private fun openSettings() {
        viewAction = MainAction.OpenSettings
    }

    private fun makeActionWithViewModelScopeAndSaveState(
        action: suspend () -> Unit
    ) {
        viewModelScope.launch {
            action()
            saveMainState(viewState)
        }
    }
}