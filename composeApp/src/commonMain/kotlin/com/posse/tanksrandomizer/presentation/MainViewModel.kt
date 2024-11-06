package com.posse.tanksrandomizer.presentation

import androidx.lifecycle.ViewModel
import com.posse.tanksrandomizer.presentation.model.MainEvent
import com.posse.tanksrandomizer.presentation.model.MainState
import com.posse.tanksrandomizer.presentation.model.isDefault
import com.posse.tanksrandomizer.presentation.model.reverseSelected
import com.posse.tanksrandomizer.presentation.use_cases.GenerateFilter
import com.posse.tanksrandomizer.presentation.use_cases.GetState
import com.posse.tanksrandomizer.presentation.use_cases.SaveState
import com.posse.tanksrandomizer.repository.SettingsRepository
import com.posse.tanksrandomizer.repository.model.FilterObjects.Experience
import com.posse.tanksrandomizer.repository.model.FilterObjects.ItemStatus
import com.posse.tanksrandomizer.repository.model.FilterObjects.Level
import com.posse.tanksrandomizer.repository.model.FilterObjects.Nation
import com.posse.tanksrandomizer.repository.model.FilterObjects.Pinned
import com.posse.tanksrandomizer.repository.model.FilterObjects.Status
import com.posse.tanksrandomizer.repository.model.FilterObjects.SwitchItem
import com.posse.tanksrandomizer.repository.model.FilterObjects.TankType
import com.posse.tanksrandomizer.repository.model.FilterObjects.Type
import com.posse.tanksrandomizer.utils.BoxedInt
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class MainViewModel(
    repository: SettingsRepository
) : ViewModel() {
    private var _state: MutableStateFlow<MainState> = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state.asStateFlow()

    private val generateFilterUseCase = GenerateFilter()
    private val saveState = SaveState(repository = repository)
    private val getState = GetState(repository = repository)

    init {
        _state.update { getState(state.value) }
    }

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
        }
    }

    private fun generateFilter() {
        _state.update { generateFilterUseCase(state.value) }

        saveState(state.value)
    }

    private fun generateNumber() {
        _state.update {
            it.copy(
                generatedQuantity = BoxedInt(Random.nextInt(1, state.value.quantity + 1))
            )
        }
    }

    private fun changeQuantity(add: Int) {
        _state.update { state ->
            state.copy(
                quantity = (state.quantity + add).coerceIn(1, 999)
            )
        }

        saveState(state.value)
    }

    private fun resetFilter() {
        if (state.value.isDefault) {
            _state.update { it.reverseSelected() }
        } else {
            _state.update {
                MainState(
                    quantity = it.quantity,
                    generatedQuantity = it.generatedQuantity
                )
            }
        }

        saveState(state.value)
    }

    private fun resetQuantity() {
        _state.update { it.copy(quantity = 1, generatedQuantity = BoxedInt(1)) }

        saveState(state.value)
    }

    private fun <T : ItemStatus<T>> MutableStateFlow<MainState>.changeItem(item: T) {
        update { state ->
            state.copy(
                levels = if (item is Level) state.levels.changeSelected(item) else state.levels,
                experiences = if (item is Experience) state.experiences.changeSelected(item) else state.experiences,
                nations = if (item is Nation) state.nations.changeSelected(item) else state.nations,
                pinned = if (item is Pinned) state.pinned.changeSelected(item) else state.pinned,
                statuses = if (item is Status) state.statuses.changeSelected(item) else state.statuses,
                tankTypes = if (item is TankType) state.tankTypes.changeSelected(item) else state.tankTypes,
                types = if (item is Type) state.types.changeSelected(item) else state.types,
            )
        }

        saveState(state.value)
    }

    private fun <T : ItemStatus<T>> List<T>.changeSelected(
        oldItem: Any
    ): List<T> {
        val changedItems = if (oldItem is SwitchItem) {
            val allSelected = all { it.selected }
            val anyRandom = any { it.random }
            if (allSelected && !anyRandom) {
                map { item -> item.copy(selected = false) }
            } else {
                map { item -> item.copy(selected = true) }
            }
        } else {
            map { item ->
                if (item == oldItem) item.copy(selected = !item.selected) else item
            }
        }

        val switchItemSelected = changedItems.any { it.selected }

        return changedItems.map { item ->
            if (item is SwitchItem) item.copy(selected = switchItemSelected)
            else item
        }
    }
}