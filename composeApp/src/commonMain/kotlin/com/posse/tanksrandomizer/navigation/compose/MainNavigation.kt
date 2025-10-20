package com.posse.tanksrandomizer.navigation.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.posse.tanksrandomizer.common.compose.utils.ErrorHandler
import com.posse.tanksrandomizer.common.data.networking.ApiErrorResponse
import com.posse.tanksrandomizer.common.data.networking.parseApiError
import com.posse.tanksrandomizer.common.domain.models.ResponseStatus
import com.posse.tanksrandomizer.navigation.compose.components.NavigationHost
import com.posse.tanksrandomizer.navigation.presentation.models.ErrorResponse
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.auth_cancel
import tanks_randomizer.composeapp.generated.resources.auth_error
import tanks_randomizer.composeapp.generated.resources.auth_expired
import tanks_randomizer.composeapp.generated.resources.something_went_wrong

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainNavigation(
    showRotation: Boolean = false,
    runningAsOverlay: Boolean = false,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = Color.Transparent,
        modifier = modifier,
    ) {
        NavigationHost(
            showRotation = showRotation,
            runningAsOverlay = runningAsOverlay,
            onRedirectError = { error ->
                if (error.status == ResponseStatus.ERROR.value) {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = getErrorMessage(error)
                        )
                    }
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}

private suspend fun getErrorMessage(errorResponse: ErrorResponse): String {
    val defaultError = getString(Res.string.something_went_wrong)

    val code = try {
        errorResponse.code?.toInt() ?: return defaultError
    } catch (_: Exception) {
        return defaultError
    }

    return when (code) {
        401 -> getString(Res.string.auth_cancel)
        403 -> getString(Res.string.auth_expired)
        410 -> getString(Res.string.auth_error)
        else -> {
            val error = parseApiError(
                ApiErrorResponse(
                    code = code,
                    message = errorResponse.message ?: return defaultError,
                    field = null,
                    value = null
                )
            )

            ErrorHandler.getErrorMessage(error)
        }
    }
}
