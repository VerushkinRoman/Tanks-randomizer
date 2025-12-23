package com.posse.tanksrandomizer.feature_online_navigation.navigation.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.posse.tanksrandomizer.common.core.di.Inject
import com.posse.tanksrandomizer.common.data.networking.EndpointConstants.REDIRECT_URL
import com.posse.tanksrandomizer.common.domain.models.Token
import com.posse.tanksrandomizer.common.domain.repository.AccountRepository
import com.posse.tanksrandomizer.feature_online_navigation.common.presentation.interactor.OnlineScreensInteractor
import com.posse.tanksrandomizer.feature_online_navigation.common.presentation.models.OnlineScreenNavigationData
import com.posse.tanksrandomizer.feature_online_navigation.feature_main_screen.compose.MainScreen
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.compose.OnlineScreen
import com.posse.tanksrandomizer.feature_online_navigation.feature_webview_screen.compose.WebViewScreen
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.models.ErrorResponse
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.models.RedirectResult
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.models.SuccessResponse
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.screens.MainScreenRoute
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.screens.OnlineScreenRoute
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.screens.WebViewScreenRoute
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.screens.onlineNavigationConfig
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.util.RedirectParser
import org.kodein.di.compose.rememberInstance
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.ExperimentalTime

@Composable
fun OnlineNavigation(
    screenId: String,
    onSuccessLogin: (screenId: String, name: String, token: Token) -> Unit,
    runningAsOverlay: Boolean,
    onRedirectError: (ErrorResponse) -> Unit,
    modifier: Modifier = Modifier
) {
    val interactor: OnlineScreensInteractor by rememberInstance()
    val screen = remember(interactor) { interactor.getOnlineScreen(screenId) }

    val startDestination = remember(screen) { getStartOnlineDestination(screenId, screen?.accountId) }
    val navBackStack = rememberNavBackStack(onlineNavigationConfig, startDestination)

    NavDisplay(
        backStack = navBackStack,
        contentAlignment = Alignment.Center,
        onBack = {},
        modifier = modifier,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(
                removeViewModelStoreOnPop = { true }
            ),
        ),
        entryProvider = entryProvider {
            entry<MainScreenRoute> {
                MainScreen(
                    toWebViewScreen = { url ->
                        navBackStack.add(WebViewScreenRoute(url))
                    },
                    modifier = Modifier.fillMaxSize(),
                )
            }

            entry<OnlineScreenRoute> { route ->
                OnlineScreen(
                    navigationData = route.navigationData,
                    logOut = {
                        navBackStack.add(MainScreenRoute)
                        if (navBackStack.size > 1) {
                            navBackStack.subList(0, navBackStack.size - 1).clear()
                        }
                    },
                    runningAsOverlay = runningAsOverlay,
                    modifier = Modifier.fillMaxSize(),
                )
            }

            entry<WebViewScreenRoute> { key ->
                WebViewScreen(
                    url = key.url,
                    runningAsOverlay = runningAsOverlay,
                    goBack = { navBackStack.removeLastOrNull() },
                    onResult = { resultUrl ->
                        handleWebViewResult(
                            resultUrl = resultUrl,
                            setSuccessResult = { name, token ->
                                screen ?: return@handleWebViewResult
                                onSuccessLogin(
                                    /* id = */screenId,
                                    /* name = */name ?: (screen.metadata.position + 1).toString(),
                                    /* token = */token
                                )
                            },
                            onRedirectError = onRedirectError,
                            navigateBack = { navBackStack.removeLastOrNull() },
                            navigateToOnlineScreen = { accountId ->
                                navBackStack.clear()
                                navBackStack.add(
                                    OnlineScreenRoute(
                                        navigationData = OnlineScreenNavigationData(
                                            id = screenId,
                                            accountId = accountId,
                                        )
                                    )
                                )
                            }
                        )
                    },
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    )
}

private fun handleWebViewResult(
    resultUrl: String,
    setSuccessResult: (name: String?, token: Token) -> Unit,
    onRedirectError: (ErrorResponse) -> Unit,
    navigateBack: () -> Unit,
    navigateToOnlineScreen: (accountId: Int) -> Unit,
) {
    @Suppress("MoveVariableDeclarationIntoWhen", "RedundantSuppression")
    val result = RedirectParser.parse(resultUrl.removePrefix("${REDIRECT_URL}?"))
    when (result) {
        is RedirectResult.Success -> handleRedirectSuccessResult(
            successResponse = result.success,
            setSuccessResult = setSuccessResult,
            navigateToOnlineScreen = navigateToOnlineScreen,
            onRedirectError = { errorMessage ->
                onRedirectError(ErrorResponse.default.copy(message = errorMessage))
                navigateBack()
            },
        )

        is RedirectResult.Error -> {
            onRedirectError(result.error)
            navigateBack()
        }

        else -> {
            onRedirectError(ErrorResponse.default.copy(message = "No params in deeplink"))
            navigateBack()
        }
    }
}

private fun handleRedirectSuccessResult(
    successResponse: SuccessResponse,
    setSuccessResult: (name: String?, token: Token) -> Unit,
    navigateToOnlineScreen: (accountId: Int) -> Unit,
    onRedirectError: (errorMessage: String) -> Unit,
) {
    val errorParams = mutableListOf<String>()

    val accountId = try {
        successResponse.accountId
    } catch (_: Exception) {
        errorParams.add("account ID: ${successResponse.accountId}")
        null
    }

    val expiresAt = try {
        successResponse.expiresAt?.toLong()
    } catch (_: Exception) {
        errorParams.add("expires at: ${successResponse.expiresAt}")
        null
    }

    val accessToken = if (successResponse.accessToken != null) {
        successResponse.accessToken
    } else {
        errorParams.add("access token: null")
        null
    }

    if (accountId != null && expiresAt != null && accessToken != null) {
        setSuccessResult(
            /* name = */successResponse.nickname,
            /* token = */
            Token(
                accessToken = accessToken,
                accountId = accountId,
                expiresAt = expiresAt,
            ),
        )

        navigateToOnlineScreen(accountId)
    } else {
        onRedirectError("Params error: ${errorParams.joinToString(", ")}")
    }
}

@OptIn(ExperimentalTime::class)
private fun getStartOnlineDestination(
    id: String,
    accountId: Int?,
): NavKey {
    accountId ?: return MainScreenRoute
    val token = Inject.instance<AccountRepository>().getToken(accountId) ?: return MainScreenRoute
    val tokenExpired = token.expiresAt - 1.days.inWholeSeconds <= Clock.System.now().epochSeconds

    return if (tokenExpired) {
        MainScreenRoute
    } else {
        OnlineScreenRoute(navigationData = OnlineScreenNavigationData(id, accountId))
    }
}
