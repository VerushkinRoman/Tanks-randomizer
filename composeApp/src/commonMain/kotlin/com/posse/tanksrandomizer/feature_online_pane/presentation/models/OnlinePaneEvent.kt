package com.posse.tanksrandomizer.feature_online_pane.presentation.models

import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Level
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Nation
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.TankType
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Type
import com.posse.tanksrandomizer.feature_online_pane.domain.models.OnlineFilterObjects.Mastery

sealed interface OnlinePaneEvent {
    data object ClearAction : OnlinePaneEvent
    class LevelPressed(val level: Level) : OnlinePaneEvent
    class MasteryPressed(val mastery: Mastery) : OnlinePaneEvent
    class NationPressed(val nation: Nation) : OnlinePaneEvent
    class TankTypePressed(val tankType: TankType) : OnlinePaneEvent
    class TypePressed(val type: Type) : OnlinePaneEvent
    data object TrashFilterPressed : OnlinePaneEvent
    data object GenerateTankPressed : OnlinePaneEvent
    data object SettingsPressed : OnlinePaneEvent
    data object RefreshTanksWikiPressed: OnlinePaneEvent
    data object RefreshAccountPressed: OnlinePaneEvent
    data object LestaLoginPressed: OnlinePaneEvent
}