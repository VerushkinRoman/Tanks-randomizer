package com.posse.tanksrandomizer.navigation.compose.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
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
import com.posse.tanksrandomizer.feature_main_screen.compose.MainScreen
import com.posse.tanksrandomizer.feature_offline_screen.compose.OfflineScreen
import com.posse.tanksrandomizer.feature_online_screen.compose.OnlineScreen
import com.posse.tanksrandomizer.feature_webview_screen.compose.WebViewScreen
import com.posse.tanksrandomizer.navigation.domain.repository.NavigationRepository
import com.posse.tanksrandomizer.navigation.presentation.models.ErrorResponse
import com.posse.tanksrandomizer.navigation.presentation.models.RedirectResult
import com.posse.tanksrandomizer.navigation.presentation.models.SuccessResponse
import com.posse.tanksrandomizer.navigation.presentation.screens.ScreenRoute
import com.posse.tanksrandomizer.navigation.presentation.screens.ScreenRoute.MainScreenRoute
import com.posse.tanksrandomizer.navigation.presentation.screens.ScreenRoute.OfflineScreenRoute
import com.posse.tanksrandomizer.navigation.presentation.screens.ScreenRoute.OnlineScreenRoute
import com.posse.tanksrandomizer.navigation.presentation.screens.ScreenRoute.WebViewScreenRoute
import com.posse.tanksrandomizer.navigation.presentation.screens.toRoute
import com.posse.tanksrandomizer.navigation.presentation.util.RedirectParser
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NavigationHost(
    navController: NavHostController = rememberNavController(),
    showRotation: Boolean,
    runningAsOverlay: Boolean,
    onRedirectError: (ErrorResponse) -> Unit,
    modifier: Modifier = Modifier
) {
    val accountRepository: AccountRepository = remember { Inject.instance() }
    val navigationRepository: NavigationRepository = remember { Inject.instance() }

    NavHost(
        navController = navController,
        startDestination = getStartDestination(
            accountRepository = accountRepository,
            navigationRepository = navigationRepository
        ),
        modifier = modifier
    ) {
        composable<MainScreenRoute> {
            MainScreen(
                toOfflineScreen = {
                    navController.navigate(OfflineScreenRoute) {
                        popUpTo<MainScreenRoute> { inclusive = true }
                    }
                },
                toWebViewScreen = { url ->
                    navController.navigate(WebViewScreenRoute(url = url))
                },
                modifier = Modifier.fillMaxSize()
            )

            LaunchedEffect(true) {
                navigationRepository.setCurrentScreenRoute(MainScreenRoute.toRoute())
            }
        }

        composable<OfflineScreenRoute> {
            OfflineScreen(
                logIn = { url ->
                    navController.navigate(WebViewScreenRoute(url = url))
                },
                showRotation = showRotation,
                runningAsOverlay = runningAsOverlay,
                modifier = Modifier.fillMaxSize()
            )

            LaunchedEffect(true) {
                navigationRepository.setCurrentScreenRoute(OfflineScreenRoute.toRoute())
            }
        }

        composable<OnlineScreenRoute> {
            OnlineScreen(
                logOut = {
                    if (!navController.popBackStack(route = MainScreenRoute, inclusive = false)) {
                        navController.navigate(MainScreenRoute) {
                            popUpTo<OnlineScreenRoute> { inclusive = true }
                        }
                    }
                },
                showRotation = showRotation,
                runningAsOverlay = runningAsOverlay,
                modifier = Modifier.fillMaxSize()
            )

            LaunchedEffect(true) {
                navigationRepository.setCurrentScreenRoute(OnlineScreenRoute.toRoute())
            }
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

@OptIn(ExperimentalTime::class)
private fun getStartDestination(
    accountRepository: AccountRepository,
    navigationRepository: NavigationRepository,
): ScreenRoute {
    val screenRoute: ScreenRoute = when (navigationRepository.getScreenRoute()) {
        MainScreenRoute.toRoute() -> MainScreenRoute
        OnlineScreenRoute.toRoute() -> OnlineScreenRoute
        OfflineScreenRoute.toRoute() -> OfflineScreenRoute
        else -> MainScreenRoute
    }

    val token: Token = accountRepository.getToken() ?: return screenRoute
    val tokenExpired = token.expiresAt - 1.days.inWholeSeconds <= Clock.System.now().epochSeconds

    return if (tokenExpired) {
        MainScreenRoute
    } else {
        screenRoute
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
