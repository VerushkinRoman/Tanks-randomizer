package com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation

import com.posse.tanksrandomizer.common.domain.models.Token
import com.posse.tanksrandomizer.common.domain.repository.AccountRepository
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.PagedScreensViewModel
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.OnlineScreenData
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.repository.OnlineScreenRepository
import com.posse.tanksrandomizer.feature_online_navigation.common.presentation.interactor.OnlineScreensInteractor
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.models.PagedOnlineScreensEvent
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.use_cases.AddNewOnlineScreen
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.use_cases.GetPagedOnlineScreenStartState
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.use_cases.RemoveOnlineScreen
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.use_cases.SetSuccessLoginResult

class PagedOnlineScreensViewModel(
    accountRepository: AccountRepository,
    onlineScreensInteractor: OnlineScreensInteractor,
    onlineScreenRepository: OnlineScreenRepository,
) : PagedScreensViewModel(
    screensInteractor = onlineScreensInteractor,
    getPagedScreenStartState = GetPagedOnlineScreenStartState(onlineScreensInteractor),
) {
    private val setSuccessLoginResult = SetSuccessLoginResult(
        accountRepository = accountRepository,
    )

    override val removeScreen = RemoveOnlineScreen(
        accountRepository = accountRepository,
        onlineScreenRepository = onlineScreenRepository,
    )

    override val addNewScreen = AddNewOnlineScreen()

    fun obtainEvent(viewEvent: PagedOnlineScreensEvent) {
        when (viewEvent) {
            is PagedOnlineScreensEvent.OnSuccessLogin -> setSuccessLoginResult(
                viewEvent.id,
                viewEvent.name,
                viewEvent.token
            )
        }
    }

    private fun setSuccessLoginResult(id: String, name: String, token: Token) {
        makeActionWithViewModelScopeAndSaveState {
            viewState.screens.filterIsInstance<OnlineScreenData>().let {
                setSuccessLoginResult(id, name, token, it)
            }
        }
    }
}
