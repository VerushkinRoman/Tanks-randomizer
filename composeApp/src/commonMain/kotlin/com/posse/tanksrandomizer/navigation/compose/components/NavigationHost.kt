package com.posse.tanksrandomizer.navigation.compose.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.navigation.NavHostController
import androidx.navigation.NavUri
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.posse.tanksrandomizer.common.core.di.Inject
import com.posse.tanksrandomizer.common.domain.models.ScreenRoute
import com.posse.tanksrandomizer.common.domain.models.Token
import com.posse.tanksrandomizer.common.domain.repository.AccountRepository
import com.posse.tanksrandomizer.common.domain.repository.NavigationRepository
import com.posse.tanksrandomizer.navigation.presentation.screens.DeepLinkResult
import com.posse.tanksrandomizer.navigation.presentation.screens.DeepLinkRoute
import com.posse.tanksrandomizer.navigation.presentation.screens.ErrorResponse
import com.posse.tanksrandomizer.navigation.presentation.screens.MainScreenRoute
import com.posse.tanksrandomizer.navigation.presentation.screens.OfflineScreenRoute
import com.posse.tanksrandomizer.navigation.presentation.screens.OnlineScreenRoute
import com.posse.tanksrandomizer.navigation.presentation.screens.SuccessResponse
import com.posse.tanksrandomizer.navigation.presentation.util.DeepLinkParser
import com.posse.tanksrandomizer.navigation.presentation.util.ExternalUriHandler
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalComposeUiApi::class, ExperimentalTime::class)
@Composable
fun NavigationHost(
    navController: NavHostController = rememberNavController(),
    onDeepLinkError: (ErrorResponse) -> Unit,
    modifier: Modifier = Modifier
) {
    val accountRepository: AccountRepository = remember { Inject.instance() }
    val navigationRepository: NavigationRepository = remember { Inject.instance() }

    val startScreen: ScreenRoute = remember {
        val token: Token = accountRepository.getToken() ?: return@remember MainScreenRoute
        val screenRoute: ScreenRoute = navigationRepository.getScreenRoute() ?: MainScreenRoute
        val tokenExpired = token.expiresAt <= Clock.System.now().epochSeconds

        if (tokenExpired) {
            MainScreenRoute
        } else {
            screenRoute
        }
    }

    DisposableEffect(Unit) {
        ExternalUriHandler.listener = { uri ->
            navController.navigate(NavUri(uri))
        }

        onDispose {
            ExternalUriHandler.listener = null
        }
    }

    NavHost(
        navController = navController,
        startDestination = startScreen,
        modifier = modifier
    ) {
        composable<DeepLinkRoute>(
            deepLinks = listOf(
                navDeepLink { uriPattern = "tanks_randomizer://main?{params}" }
            ),
        ) { backStackEntry ->
            val deepLinkRoute: DeepLinkRoute = backStackEntry.toRoute()
            val result = DeepLinkParser.parse(deepLinkRoute.params)

            when (result) {
                is DeepLinkResult.Success -> {
                    navController.navigate(OnlineScreenRoute(result.success)) {
                        popUpTo<DeepLinkRoute> { inclusive = true }
                    }
                }

                is DeepLinkResult.Error -> {
                    onDeepLinkError(result.error)
                }

                else -> {
                    onDeepLinkError(ErrorResponse.default.copy(message = "No params in deeplink"))
                }
            }
        }

        composable<MainScreenRoute> {
            MainScreen(
                toOfflineScreen = {
                    navController.navigate(
                        route = OfflineScreenRoute
                    ) {
                        popUpTo<MainScreenRoute> { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxSize()
            )

            LaunchedEffect(true) {
                navigationRepository.setCurrentScreenRoute(MainScreenRoute)
            }
        }

        composable<OfflineScreenRoute> {
            OfflineScreen(
                modifier = Modifier.fillMaxSize()
            )

            LaunchedEffect(true) {
                navigationRepository.setCurrentScreenRoute(OfflineScreenRoute)
            }
        }

        composable<OnlineScreenRoute> { backStackEntry ->
            val successResponse: SuccessResponse = backStackEntry.toRoute()
            OnlineScreen(
                response = successResponse,
                toMainScreen = {
                    if (!navController.popBackStack(route = MainScreenRoute, inclusive = false)) {
                        navController.navigate(MainScreenRoute) {
                            popUpTo<OnlineScreenRoute> { inclusive = true }
                        }
                    }
                },
            )

            LaunchedEffect(true) {
                navigationRepository.setCurrentScreenRoute(OnlineScreenRoute(successResponse))
            }
        }
    }

    BackHandler(onBack = { })
}