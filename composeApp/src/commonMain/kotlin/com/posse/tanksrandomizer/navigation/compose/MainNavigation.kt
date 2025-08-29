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
import com.posse.tanksrandomizer.common.domain.models.ResponseStatus
import com.posse.tanksrandomizer.navigation.compose.components.NavigationHost
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.something_went_wrong

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainNavigation(
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = modifier
    ) {
        NavigationHost(
            onDeepLinkError = { error ->
                if (error.status == ResponseStatus.ERROR.value) {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = error.message ?: getString(Res.string.something_went_wrong)
                        )
                    }
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}
