package com.posse.tanksrandomizer.feature_offline_pane.presentation.models

import com.posse.tanksrandomizer.common.domain.models.FilterObjects.Experience
import com.posse.tanksrandomizer.common.domain.models.FilterObjects.Level
import com.posse.tanksrandomizer.common.domain.models.FilterObjects.Nation
import com.posse.tanksrandomizer.common.domain.models.FilterObjects.Pinned
import com.posse.tanksrandomizer.common.domain.models.FilterObjects.Status
import com.posse.tanksrandomizer.common.domain.models.FilterObjects.TankType
import com.posse.tanksrandomizer.common.domain.models.FilterObjects.Type

sealed interface OfflineScreenEvent {
    data object ClearAction : OfflineScreenEvent
    class LevelPressed(val level: Level) : OfflineScreenEvent
    class ExperiencePressed(val experience: Experience) : OfflineScreenEvent
    class NationPressed(val nation: Nation) : OfflineScreenEvent
    class PinnedPressed(val pinned: Pinned) : OfflineScreenEvent
    class StatusPressed(val status: Status) : OfflineScreenEvent
    class TankTypePressed(val tankType: TankType) : OfflineScreenEvent
    class TypePressed(val type: Type) : OfflineScreenEvent
    data object PlusPressed : OfflineScreenEvent
    data object PlusTenPressed : OfflineScreenEvent
    data object PlusHundredPressed : OfflineScreenEvent
    data object MinusPressed : OfflineScreenEvent
    data object MinusTenPressed : OfflineScreenEvent
    data object MinusHundredPressed : OfflineScreenEvent
    data object TrashFilterPressed : OfflineScreenEvent
    data object GenerateFilterPressed : OfflineScreenEvent
    data object GenerateNumberPressed : OfflineScreenEvent
    data object TrashNumberPressed : OfflineScreenEvent
    data object SettingsPressed : OfflineScreenEvent
}