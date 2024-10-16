package com.posse.tanksrandomizer.presentation

import androidx.lifecycle.ViewModel
import com.posse.tanksrandomizer.presentation.model.MainEvent
import com.posse.tanksrandomizer.presentation.model.MainState
import com.posse.tanksrandomizer.presentation.model.isDefault
import com.posse.tanksrandomizer.presentation.model.reverseSelected
import com.posse.tanksrandomizer.presentation.use_cases.GenerateFilter
import com.posse.tanksrandomizer.presentation.use_cases.SaveState
import com.posse.tanksrandomizer.repository.SettingsRepository
import com.posse.tanksrandomizer.repository.model.Experience
import com.posse.tanksrandomizer.repository.model.Level
import com.posse.tanksrandomizer.repository.model.Nation
import com.posse.tanksrandomizer.repository.model.Pinned
import com.posse.tanksrandomizer.repository.model.Status
import com.posse.tanksrandomizer.repository.model.TankType
import com.posse.tanksrandomizer.repository.model.Type
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

    init {
        _state.update {
            MainState(
                levels = repository.levels,
                experiences = repository.experiences,
                nations = repository.nations,
                pinned = repository.pinned,
                statuses = repository.statuses,
                tankTypes = repository.tankTypes,
                types = repository.types,
                quantity = repository.quantity
            )
        }
    }

    fun obtainEvent(event: MainEvent) {
        when (event) {
            is MainEvent.ExperiencePressed -> changeExperiences(event.experience)
            is MainEvent.LevelPressed -> changeLevels(event.level)
            is MainEvent.NationPressed -> changeNations(event.nation)
            MainEvent.PinnedPressed -> changePinned()
            is MainEvent.StatusPressed -> changeStatuses(event.status)
            is MainEvent.TankTypePressed -> changeTankTypes(event.tankType)
            is MainEvent.TypePressed -> changeTypes(event.type)
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

    private fun changePinned() {
        _state.update {
            it.copy(pinned = Pinned.Status(selected = !state.value.pinned.selected))
        }

        saveState(state.value)
    }

    private fun changeStatuses(oldStatus: Status) {
        _state.update { state ->
            state.copy(
                statuses = state.statuses.map { status ->
                    if (status == oldStatus) {
                        status.copy(selected = !status.selected, random = false)
                    } else status
                }
            )
        }

        saveState(state.value)
    }

    private fun changeTankTypes(oldTankType: TankType) {
        _state.update { state ->
            state.copy(
                tankTypes = state.tankTypes.map { tankType ->
                    if (tankType == oldTankType) {
                        tankType.copy(selected = !tankType.selected, random = false)
                    } else tankType
                }
            )
        }

        saveState(state.value)
    }

    private fun changeTypes(oldType: Type) {
        _state.update { state ->
            state.copy(
                types = state.types.map { type ->
                    if (type == oldType) {
                        type.copy(selected = !type.selected, random = false)
                    } else type
                }
            )
        }

        saveState(state.value)
    }

    private fun changeNations(oldNation: Nation) {
        _state.update { state ->
            state.copy(
                nations = state.nations.map { nation ->
                    if (nation == oldNation) {
                        nation.copy(selected = !nation.selected, random = false)
                    } else nation
                }
            )
        }

        saveState(state.value)
    }

    private fun changeLevels(oldLevel: Level) {
        _state.update { state ->
            state.copy(
                levels = state.levels.map { level ->
                    if (level == oldLevel) {
                        level.copy(selected = !level.selected, random = false)
                    } else level
                }
            )
        }

        saveState(state.value)
    }

    private fun changeExperiences(oldExperience: Experience) {
        _state.update { state ->
            state.copy(
                experiences = state.experiences.map { experience ->
                    if (experience == oldExperience) {
                        experience.copy(selected = !experience.selected, random = false)
                    } else experience
                }
            )
        }

        saveState(state.value)
    }

    private fun generateFilter() {
        val newState = generateFilterUseCase(state.value) ?: return

        _state.update { newState }

        saveState(state.value)
    }

    private fun generateNumber() {
        _state.update {
            it.copy(generatedQuantity = BoxedInt(Random.nextInt(1, state.value.quantity + 1)))
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
                    quantity = state.value.quantity,
                    generatedQuantity = state.value.generatedQuantity
                )
            }
        }

        saveState(state.value)
    }

    private fun resetQuantity() {
        _state.update { it.copy(quantity = 1, generatedQuantity = BoxedInt(1)) }

        saveState(state.value)
    }
}