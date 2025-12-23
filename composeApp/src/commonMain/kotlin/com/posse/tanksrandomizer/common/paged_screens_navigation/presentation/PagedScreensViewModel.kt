package com.posse.tanksrandomizer.common.paged_screens_navigation.presentation

import androidx.annotation.IntRange
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.interactor.ScreensInteractor
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models.PagedScreen
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models.PagedScreensAction
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models.PagedScreensEvent
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models.PagedScreensState
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.use_cases.AddNewScreen
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.use_cases.ChangePosition
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.use_cases.GetPagedScreenStartState
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.use_cases.RemoveScreen
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.use_cases.SelectScreen
import com.posse.tanksrandomizer.common.presentation.utils.BaseSharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

abstract class PagedScreensViewModel(
    private val screensInteractor: ScreensInteractor,
    getPagedScreenStartState: GetPagedScreenStartState
) : BaseSharedViewModel<PagedScreensState, PagedScreensAction, PagedScreensEvent>(
    initialState = getPagedScreenStartState.invoke()
) {
    internal abstract val removeScreen: RemoveScreen
    internal abstract val addNewScreen: AddNewScreen
    private val changePosition = ChangePosition()
    private val selectScreen = SelectScreen()

    init {
        withViewModelScope {
            screensInteractor.screens.collect { screens ->
                viewState = viewState.copy(screens = screens)
            }
        }
    }

    override fun obtainEvent(viewEvent: PagedScreensEvent) {
        when (viewEvent) {
            PagedScreensEvent.ClearAction -> viewAction = null
            PagedScreensEvent.AddScreenPressed -> addScreen()
            is PagedScreensEvent.RemoveScreenPressed -> removeScreen(viewEvent.id)
            is PagedScreensEvent.MoveLeft -> changePosition(viewEvent.id, -1)
            is PagedScreensEvent.MoveRight -> changePosition(viewEvent.id, 1)
            is PagedScreensEvent.ScreenSelected -> selectScreen(viewEvent.id)
        }
    }

    private fun addScreen() {
        makeActionWithViewModelScopeAndSaveState {
            addNewScreen(viewState.screens).let { result ->
                result.emptyScreenPosition?.let { emptyScreenPosition ->
                    viewAction = PagedScreensAction.CantAddScreens(emptyScreenPosition)
                }

                result.screens
            }
        }
    }

    private fun removeScreen(id: String) {
        makeActionWithViewModelScopeAndSaveState {
            removeScreen(id, viewState.screens)
                .ifEmpty { addNewScreen(emptyList()).screens }
        }
    }

    private fun changePosition(id: String, @IntRange(-1, 1) diff: Int) {
        makeActionWithViewModelScopeAndSaveState {
            changePosition(id, diff, viewState.screens)
        }
    }

    private fun selectScreen(id: String) {
        if (viewState.screens.find { it.metadata.id == id }?.metadata?.selected == true) return

        makeActionWithViewModelScopeAndSaveState {
            selectScreen(id, viewState.screens)
        }
    }

    internal fun makeActionWithViewModelScopeAndSaveState(
        action: suspend CoroutineScope.() -> List<PagedScreen<*>>
    ): Job {
        return withViewModelScope {
            val screens = action()
            screensInteractor.setScreens(screens)
        }
    }
}
