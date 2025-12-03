package com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation

import androidx.annotation.IntRange
import com.posse.tanksrandomizer.common.domain.models.Token
import com.posse.tanksrandomizer.common.domain.repository.AccountRepository
import com.posse.tanksrandomizer.common.presentation.utils.BaseSharedViewModel
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.OnlineScreens
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.repository.OnlineScreenRepository
import com.posse.tanksrandomizer.feature_online_navigation.common.presentation.interactor.OnlineScreensInteractor
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.models.PagedOnlineScreensAction
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.models.PagedOnlineScreensEvent
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.models.PagedOnlineScreensState
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.use_cases.AddNewScreen
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.use_cases.ChangeName
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.use_cases.ChangePosition
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.use_cases.GetPagedOnlineScreenStartState
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.use_cases.RemoveScreen
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.use_cases.SelectScreen
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.use_cases.SetSuccessLoginResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

class PagedOnlineScreensViewModel(
    accountRepository: AccountRepository,
    private val onlineScreensInteractor: OnlineScreensInteractor,
    onlineScreenRepository: OnlineScreenRepository,
) : BaseSharedViewModel<PagedOnlineScreensState, PagedOnlineScreensAction, PagedOnlineScreensEvent>(
    initialState = GetPagedOnlineScreenStartState(onlineScreensInteractor = onlineScreensInteractor).invoke()
) {
    private val setSuccessLoginResult = SetSuccessLoginResult(
        accountRepository = accountRepository,
    )

    private val removeScreen = RemoveScreen(
        accountRepository = accountRepository,
        onlineScreenRepository = onlineScreenRepository,
    )

    private val addNewScreen = AddNewScreen()
    private val changePosition = ChangePosition()
    private val changeName = ChangeName()
    private val selectScreen = SelectScreen()

    init {
        withViewModelScope {
            onlineScreensInteractor.onlineScreens.collect { screens ->
                viewState = viewState.copy(screens = screens)
            }
        }
    }

    override fun obtainEvent(viewEvent: PagedOnlineScreensEvent) {
        when (viewEvent) {
            PagedOnlineScreensEvent.ClearAction -> viewAction = null
            PagedOnlineScreensEvent.AddScreenPressed -> addScreen()
            is PagedOnlineScreensEvent.RemoveScreenPressed -> removeScreen(viewEvent.id)
            is PagedOnlineScreensEvent.EditMenuCalled -> showEditMenu(viewEvent.id)
            PagedOnlineScreensEvent.EditMenuDismissed -> hideEditMenu()
            is PagedOnlineScreensEvent.MoveLeft -> changePosition(viewEvent.id, -1)
            is PagedOnlineScreensEvent.MoveRight -> changePosition(viewEvent.id, 1)
            is PagedOnlineScreensEvent.OnNameChanged -> changeName(viewEvent.id, viewEvent.name)
            is PagedOnlineScreensEvent.ShowNameEditField -> showEditName(viewEvent.id)
            is PagedOnlineScreensEvent.OnNameEdited -> onNameEdited(viewEvent.name)
            PagedOnlineScreensEvent.OnNameEditDismissed -> closeNameEditField()
            is PagedOnlineScreensEvent.OnlineScreenSelected -> selectScreen(viewEvent.id)
            is PagedOnlineScreensEvent.OnSuccessLogin -> setSuccessLoginResult(
                viewEvent.id,
                viewEvent.name,
                viewEvent.token
            )
        }
    }

    private fun addScreen() {
        makeActionWithViewModelScopeAndSaveState {
            addNewScreen(viewState.screens)
        }
    }

    private fun removeScreen(id: String) {
        makeActionWithViewModelScopeAndSaveState {
            if (viewState.screens.size == 1) {
                addNewScreen(emptyList())
            } else {
                removeScreen(id, viewState.screens)
            }
        }
    }

    private fun changePosition(id: String, @IntRange(-1, 1) diff: Int) {
        makeActionWithViewModelScopeAndSaveState {
            changePosition(id, diff, viewState.screens)
        }
    }

    private fun changeName(id: String, name: String) {
        makeActionWithViewModelScopeAndSaveState {
            viewState = viewState.copy(
                editMenuOnElementId = null,
                nameEditFieldValue = null,
            )
            changeName(id, name, viewState.screens)
        }
    }

    private fun showEditMenu(id: String) {
        viewState = viewState.copy(editMenuOnElementId = id)
    }

    private fun hideEditMenu() {
        viewState = viewState.copy(editMenuOnElementId = null)
    }

    private fun selectScreen(id: String) {
        if (viewState.screens.find { it.id == id }?.selected == true) return

        makeActionWithViewModelScopeAndSaveState {
            selectScreen(id, viewState.screens)
        }
    }

    private fun closeNameEditField() {
        viewState = viewState.copy(
            editMenuOnElementId = null,
            nameEditFieldValue = null,
        )
    }

    private fun setSuccessLoginResult(id: String, name: String, token: Token) {
        makeActionWithViewModelScopeAndSaveState {
            setSuccessLoginResult(id, name, token, viewState.screens)
        }
    }

    private fun onNameEdited(name: String) {
        val shortName = name.take(MAX_NAME_LENGTH)
        viewState = viewState.copy(nameEditFieldValue = shortName)
    }

    private fun showEditName(id: String) {
        val name = viewState.screens.find { it.id == id }?.name ?: return
        viewState = viewState.copy(nameEditFieldValue = name)
    }

    private fun makeActionWithViewModelScopeAndSaveState(
        action: suspend CoroutineScope.() -> OnlineScreens
    ): Job {
        return withViewModelScope {
            val screens = action()
            onlineScreensInteractor.setOnlineScreens(screens)
        }
    }

    companion object {
        private const val MAX_NAME_LENGTH = 25
    }
}
