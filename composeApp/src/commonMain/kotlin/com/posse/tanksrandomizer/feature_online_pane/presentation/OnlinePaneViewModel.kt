package com.posse.tanksrandomizer.feature_online_pane.presentation

import androidx.lifecycle.viewModelScope
import com.posse.tanksrandomizer.common.core.di.Inject
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.ItemStatus
import com.posse.tanksrandomizer.common.domain.models.RepositoryType
import com.posse.tanksrandomizer.common.domain.repository.CommonRepository
import com.posse.tanksrandomizer.common.domain.utils.Dispatchers
import com.posse.tanksrandomizer.common.presentation.utils.BaseSharedViewModel
import com.posse.tanksrandomizer.feature_online_pane.domain.repository.OnlineRepository
import com.posse.tanksrandomizer.feature_online_pane.presentation.models.OnlinePaneAction
import com.posse.tanksrandomizer.feature_online_pane.presentation.models.OnlinePaneEvent
import com.posse.tanksrandomizer.feature_online_pane.presentation.models.OnlinePaneState
import com.posse.tanksrandomizer.feature_online_pane.presentation.use_cases.GenerateOnlineFilter
import com.posse.tanksrandomizer.feature_online_pane.presentation.use_cases.GetOnlinePaneStartState
import com.posse.tanksrandomizer.feature_online_pane.presentation.use_cases.SaveOnlinePaneState
import kotlinx.coroutines.launch

class OnlinePaneViewModel(
    filterRepository: CommonRepository = Inject.instance(tag = RepositoryType.Online),
    onlineRepository: OnlineRepository = Inject.instance(),
    dispatchers: Dispatchers = Inject.instance(),
) : BaseSharedViewModel<OnlinePaneState, OnlinePaneAction, OnlinePaneEvent>(
    initialState = GetOnlinePaneStartState(
        commonRepository = filterRepository,
        onlineRepository = onlineRepository,
    ).invoke()
) {
    private val generateOnlineFilter = GenerateOnlineFilter(dispatchers = dispatchers)
    private val saveOnlinePaneState = SaveOnlinePaneState(
        filterRepository = filterRepository,
        onlineRepository = onlineRepository,
        dispatchers = dispatchers,
    )

    override fun obtainEvent(viewEvent: OnlinePaneEvent) {
        when (viewEvent) {
            OnlinePaneEvent.ClearAction -> viewAction = null
            is OnlinePaneEvent.LevelPressed -> viewState.changeItem(viewEvent.level)
            is OnlinePaneEvent.MasteryPressed -> viewState.changeItem(viewEvent.mastery)
            is OnlinePaneEvent.NationPressed -> viewState.changeItem(viewEvent.nation)
            is OnlinePaneEvent.TankTypePressed -> viewState.changeItem(viewEvent.tankType)
            is OnlinePaneEvent.TypePressed -> viewState.changeItem(viewEvent.type)
            OnlinePaneEvent.GenerateTankPressed -> TODO("generateTank()")
            OnlinePaneEvent.LestaLoginPressed -> TODO("login()")
            OnlinePaneEvent.RefreshAccountPressed -> TODO("refreshAccount()")
            OnlinePaneEvent.RefreshTanksWikiPressed -> TODO("refreshTanksWiki()")
            OnlinePaneEvent.SettingsPressed -> toggleSettings()
            OnlinePaneEvent.TrashFilterPressed -> TODO("resetFilter()")
        }
    }

    private fun toggleSettings() {
        viewAction = OnlinePaneAction.ToggleSettings
    }

    @Suppress("UnusedReceiverParameter")
    private fun <T : ItemStatus<T>> OnlinePaneState.changeItem(item: T) {
        makeActionWithViewModelScopeAndSaveState {
            viewState = viewState.copy(
                onlineFilters = viewState.onlineFilters.changeItem(item),
            )
        }
    }

    private fun makeActionWithViewModelScopeAndSaveState(
        action: suspend () -> Unit
    ) {
        viewModelScope.launch {
            action()
            saveOnlinePaneState(viewState)
        }
    }
}