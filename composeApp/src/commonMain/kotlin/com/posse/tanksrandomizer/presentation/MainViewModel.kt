package com.posse.tanksrandomizer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.posse.tanksrandomizer.presentation.model.Filters
import com.posse.tanksrandomizer.presentation.model.MainEvent
import com.posse.tanksrandomizer.presentation.model.MainState
import com.posse.tanksrandomizer.presentation.model.Numbers
import com.posse.tanksrandomizer.presentation.model.isDefault
import com.posse.tanksrandomizer.presentation.model.reverseSelected
import com.posse.tanksrandomizer.presentation.use_cases.GenerateFilter
import com.posse.tanksrandomizer.presentation.use_cases.GetStartState
import com.posse.tanksrandomizer.presentation.use_cases.SaveState
import com.posse.tanksrandomizer.repository.SettingsRepository
import com.posse.tanksrandomizer.repository.SettingsRepositoryImpl
import com.posse.tanksrandomizer.repository.model.FilterObjects.ItemStatus
import com.posse.tanksrandomizer.utils.BoxedInt
import com.posse.tanksrandomizer.utils.Dispatchers
import com.posse.tanksrandomizer.utils.DispatchersImpl
import com.posse.tanksrandomizer.utils.RotateDirection
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainViewModel(
    repository: SettingsRepository = SettingsRepositoryImpl.getInstance(),
    dispatchers: Dispatchers = DispatchersImpl()
) : ViewModel() {
    private var _state: MutableStateFlow<MainState> = MutableStateFlow(GetStartState().invoke())
    val state: StateFlow<MainState> = _state.asStateFlow()

    private val generateFilterUseCase = GenerateFilter(
        dispatchers = dispatchers
    )
    private val saveState = SaveState(
        repository = repository,
        dispatchers = dispatchers,
    )

    fun obtainEvent(event: MainEvent) {
        when (event) {
            is MainEvent.ExperiencePressed -> _state.changeItem(event.experience)
            is MainEvent.LevelPressed -> _state.changeItem(event.level)
            is MainEvent.NationPressed -> _state.changeItem(event.nation)
            is MainEvent.PinnedPressed -> _state.changeItem(event.pinned)
            is MainEvent.StatusPressed -> _state.changeItem(event.status)
            is MainEvent.TankTypePressed -> _state.changeItem(event.tankType)
            is MainEvent.TypePressed -> _state.changeItem(event.type)
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
            MainEvent.AutoRotatePressed -> enableAutorotation()
            MainEvent.LandscapeRotatePressed -> changeRotationToLandscape()
            MainEvent.LockRotatePressed -> disableAutorotation()
            MainEvent.PortraitRotatePressed -> changeRotationPortrait()
        }
    }

    private fun generateFilter() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    filters = generateFilterUseCase(it.filters),
                )
            }

            saveState(state.value)
        }
    }

    private fun generateNumber() {
        _state.update {
            it.copy(
                numbers = it.numbers.copy(
                    generatedQuantity = BoxedInt(Random.nextInt(1, it.numbers.quantity + 1)),
                ),
            )
        }
    }

    private fun changeQuantity(add: Int) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    numbers = it.numbers.copy(
                        quantity = (it.numbers.quantity + add).coerceIn(1, 999),
                    ),
                )
            }

            saveState(state.value)
        }
    }

    private fun resetFilter() {
        viewModelScope.launch {
            if (state.value.filters.isDefault) {
                _state.update {
                    it.copy(
                        filters = it.filters.reverseSelected(),
                    )
                }
            } else {
                _state.update {
                    it.copy(
                        filters = Filters(),
                    )
                }
            }

            saveState(state.value)
        }
    }

    private fun resetQuantity() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    numbers = Numbers(),
                )
            }

            saveState(state.value)
        }
    }

    private fun <T : ItemStatus<T>> MutableStateFlow<MainState>.changeItem(item: T) {
        viewModelScope.launch {
            update { state ->
                state.copy(
                    filters = state.filters.changeItem(item),
                )
            }

            saveState(state.value)
        }
    }

    private fun enableAutorotation() {
        if (_state.value.rotation.autoRotateEnabled) return

        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    rotation = state.rotation.copy(
                        autoRotateEnabled = true
                    )
                )
            }

            saveState(state.value)
        }
    }

    private fun disableAutorotation() {
        if (!_state.value.rotation.autoRotateEnabled) return

        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    rotation = state.rotation.copy(
                        autoRotateEnabled = false
                    )
                )
            }

            saveState(state.value)
        }
    }

    private fun changeRotationToLandscape() {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    rotation = state.rotation.copy(
                        rotateDirection = RotateDirection.Landscape,
                    )
                )
            }

            saveState(state.value)
        }
    }

    private fun changeRotationPortrait() {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    rotation = state.rotation.copy(
                        rotateDirection = RotateDirection.Portrait
                    )
                )
            }

            saveState(state.value)
        }
    }
}