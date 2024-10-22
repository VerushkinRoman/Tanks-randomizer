package com.posse.tanksrandomizer.presentation.model

import com.posse.tanksrandomizer.repository.model.FilterObjects.Experience
import com.posse.tanksrandomizer.repository.model.FilterObjects.Level
import com.posse.tanksrandomizer.repository.model.FilterObjects.Nation
import com.posse.tanksrandomizer.repository.model.FilterObjects.Pinned
import com.posse.tanksrandomizer.repository.model.FilterObjects.Status
import com.posse.tanksrandomizer.repository.model.FilterObjects.TankType
import com.posse.tanksrandomizer.repository.model.FilterObjects.Type

sealed interface MainEvent {
    class LevelPressed(val level: Level) : MainEvent
    class ExperiencePressed(val experience: Experience) : MainEvent
    class NationPressed(val nation: Nation) : MainEvent
    class PinnedPressed(val pinned: Pinned) : MainEvent
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