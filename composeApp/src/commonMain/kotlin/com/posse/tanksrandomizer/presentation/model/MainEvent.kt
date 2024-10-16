package com.posse.tanksrandomizer.presentation.model

import com.posse.tanksrandomizer.repository.model.Experience
import com.posse.tanksrandomizer.repository.model.Level
import com.posse.tanksrandomizer.repository.model.Nation
import com.posse.tanksrandomizer.repository.model.Status
import com.posse.tanksrandomizer.repository.model.TankType
import com.posse.tanksrandomizer.repository.model.Type

sealed interface MainEvent {
    class LevelPressed(val level: Level) : MainEvent
    class ExperiencePressed(val experience: Experience) : MainEvent
    class NationPressed(val nation: Nation) : MainEvent
    data object PinnedPressed : MainEvent
    class StatusPressed(val status: Status) : MainEvent
    class TankTypePressed(val tankType: TankType) : MainEvent
    class TypePressed(val type: Type) : MainEvent
    data object PlusPressed : MainEvent
    data object PlusTenPressed : MainEvent
    data object PlusHundredPressed : MainEvent
    data object MinusPressed : MainEvent
    data object MinusTenPressed : MainEvent
    data object MinusHundredPressed : MainEvent
    data object TrashFilterPressed : MainEvent
    data object GenerateFilterPressed : MainEvent
    data object GenerateNumberPressed : MainEvent
    data object TrashNumberPressed : MainEvent
}