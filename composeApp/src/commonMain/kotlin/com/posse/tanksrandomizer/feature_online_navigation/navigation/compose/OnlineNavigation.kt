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
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.ExperimentalTime

@Composable
fun OnlineNavigation(
    runningAsOverlay: Boolean,
    onRedirectError: (ErrorResponse) -> Unit,
    modifier: Modifier = Modifier
) {
    val accountRepository: AccountRepository = remember { Inject.instance() }

    val startDestination =
        remember(accountRepository) { getStartOnlineDestination(accountRepository) }
    val navBackStack = rememberNavBackStack(onlineNavigationConfig, startDestination)

    NavDisplay(
        backStack = navBackStack,
        contentAlignment = Alignment.Center,
        onBack = {},
        modifier = modifier,
        entryDecorators =
            listOf(
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

            entry<OnlineScreenRoute> {
                OnlineScreen(
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
                            accountRepository = accountRepository,
                            onRedirectError = onRedirectError,
                            navigateBack = { navBackStack.removeLastOrNull() },
                            navigateToOnlineScreen = {
                                navBackStack.clear()
                                navBackStack.add(OnlineScreenRoute)
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
    accountRepository: AccountRepository,
    onRedirectError: (ErrorResponse) -> Unit,
    navigateBack: () -> Unit,
    navigateToOnlineScreen: () -> Unit
) {
    @Suppress("MoveVariableDeclarationIntoWhen", "RedundantSuppression")
    val result = RedirectParser.parse(resultUrl.removePrefix("${REDIRECT_URL}?"))
    when (result) {
        is RedirectResult.Success -> handleRedirectSuccessResult(
            successResponse = result.success,
            accountRepository = accountRepository,
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
    accountRepository: AccountRepository,
    navigateToOnlineScreen: () -> Unit,
    onRedirectError: (errorMessage: String) -> Unit,
) {
    val errorParams = mutableListOf<String>()

    val accountId = try {
        successResponse.accountId?.toInt()
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
        accountRepository.setToken(
            Token(
                accessToken = accessToken,
                accountId = accountId,
                expiresAt = expiresAt,
            )
        )

        accountRepository.setNickname(successResponse.nickname)

        navigateToOnlineScreen()
    } else {
        onRedirectError("Params error: ${errorParams.joinToString(", ")}")
    }
}

@OptIn(ExperimentalTime::class)
private fun getStartOnlineDestination(
    accountRepository: AccountRepository,
): NavKey {
    val token: Token = accountRepository.getToken() ?: return MainScreenRoute
    val tokenExpired = token.expiresAt - 1.days.inWholeSeconds <= Clock.System.now().epochSeconds

    return if (tokenExpired) {
        MainScreenRoute
    } else {
        OnlineScreenRoute
    }
}
