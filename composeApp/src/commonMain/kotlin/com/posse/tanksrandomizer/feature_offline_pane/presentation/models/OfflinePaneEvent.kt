package com.posse.tanksrandomizer.feature_offline_pane.presentation.models

import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Level
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Nation
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.TankType
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Type
import com.posse.tanksrandomizer.feature_offline_pane.domain.models.OfflineFilterObjects.Experience
import com.posse.tanksrandomizer.feature_offline_pane.domain.models.OfflineFilterObjects.Pinned
import com.posse.tanksrandomizer.feature_offline_pane.domain.models.OfflineFilterObjects.Status

sealed interface OfflinePaneEvent {
    data object ClearAction : OfflinePaneEvent
    class LevelPressed(val level: Level) : OfflinePaneEvent
    class ExperiencePressed(val experience: Experience) : OfflinePaneEvent
    class NationPressed(val nation: Nation) : OfflinePaneEvent
    class PinnedPressed(val pinned: Pinned) : OfflinePaneEvent
    class StatusPressed(val status: Status) : OfflinePaneEvent
    class TankTypePressed(val tankType: TankType) : OfflinePaneEvent
    class TypePressed(val type: Type) : OfflinePaneEvent
    data object PlusPressed : OfflinePaneEvent
    data object PlusTenPressed : OfflinePaneEvent
    data object PlusHundredPressed : OfflinePaneEvent
    data object MinusPressed : OfflinePaneEvent
    data object MinusTenPressed : OfflinePaneEvent
    data object MinusHundredPressed : OfflinePaneEvent
    data object TrashFilterPressed : OfflinePaneEvent
    data object GenerateFilterPressed : OfflinePaneEvent
    data object GenerateNumberPressed : OfflinePaneEvent
    data object TrashNumberPressed : OfflinePaneEvent
    data object SettingsPressed : OfflinePaneEvent
}