package com.posse.tanksrandomizer.feature_online_navigation.navigation.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.posse.tanksrandomizer.common.core.di.Inject
import com.posse.tanksrandomizer.common.data.networking.EndpointConstants.REDIRECT_URL
import com.posse.tanksrandomizer.common.domain.models.Token
import com.posse.tanksrandomizer.common.domain.repository.AccountRepository
import com.posse.tanksrandomizer.feature_online_navigation.feature_main_screen.compose.MainScreen
import com.posse.tanksrandomizer.feature_online_navigation.feature_main_screen.presentation.MainScreenViewModel
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.compose.OnlineScreen
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.presentation.OnlineScreenViewModel
import com.posse.tanksrandomizer.feature_online_navigation.feature_webview_screen.compose.WebViewScreen
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.models.ErrorResponse
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.models.RedirectResult
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.models.SuccessResponse
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.screens.OnlineNavigationRoute
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.screens.OnlineNavigationRoute.MainScreenRoute
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.screens.OnlineNavigationRoute.OnlineScreenRoute
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.screens.OnlineNavigationRoute.WebViewScreenRoute
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.util.RedirectParser
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.ExperimentalTime

@Composable
fun OnlineNavigation(
    navController: NavHostController = rememberNavController(),
    runningAsOverlay: Boolean,
    onRedirectError: (ErrorResponse) -> Unit,
    modifier: Modifier = Modifier
) {
    val accountRepository: AccountRepository = remember { Inject.instance() }

    val startDestination: OnlineNavigationRoute = remember(accountRepository) {
        getStartOnlineDestination(accountRepository)
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable<MainScreenRoute> { backStackEntry ->
            val viewModel = remember(backStackEntry) { MainScreenViewModel() }

            DisposableEffect(viewModel) {
                onDispose {
                    viewModel.onCleared()
                }
            }

            MainScreen(
                viewModel = viewModel,
                toWebViewScreen = { url ->
                    navController.navigate(WebViewScreenRoute(url = url)) {
                        launchSingleTop = true
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }

        composable<OnlineScreenRoute> { backStackEntry ->
            val viewModel = remember(backStackEntry) { OnlineScreenViewModel() }

            DisposableEffect(viewModel) {
                onDispose {
                    viewModel.onCleared()
                }
            }

            OnlineScreen(
                viewModel = viewModel,
                logOut = {
                    navController.navigate(MainScreenRoute) {
                        popUpTo<OnlineScreenRoute> { inclusive = true }
                    }
                },
                runningAsOverlay = runningAsOverlay,
                modifier = Modifier.fillMaxSize()
            )
        }

        composable<WebViewScreenRoute> { backStackEntry ->
            val url: String = backStackEntry.toRoute<WebViewScreenRoute>().url

            WebViewScreen(
                url = url,
                runningAsOverlay = runningAsOverlay,
                goBack = { navController.navigateUp() },
                onResult = { resultUrl ->
                    handleWebViewResult(
                        resultUrl = resultUrl,
                        accountRepository = accountRepository,
                        onRedirectError = onRedirectError,
                        navigateBack = { navController.navigateUp() },
                        navigateToOnlineScreen = {
                            navController.navigate(OnlineScreenRoute) {
                                popUpTo<WebViewScreenRoute> { inclusive = true }
                            }
                        }
                    )
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
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

        navigateToOnlineScreen()
    } else {
        onRedirectError("Params error: ${errorParams.joinToString(", ")}")
    }
}

@OptIn(ExperimentalTime::class)
private fun getStartOnlineDestination(
    accountRepository: AccountRepository,
): OnlineNavigationRoute {
    val token: Token = accountRepository.getToken() ?: return MainScreenRoute
    val tokenExpired = token.expiresAt - 1.days.inWholeSeconds <= Clock.System.now().epochSeconds

    return if (tokenExpired) {
        MainScreenRoute
    } else {
        OnlineScreenRoute
    }
}
