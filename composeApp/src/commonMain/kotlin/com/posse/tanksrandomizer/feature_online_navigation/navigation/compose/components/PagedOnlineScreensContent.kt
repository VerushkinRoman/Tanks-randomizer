package com.posse.tanksrandomizer.feature_online_navigation.navigation.compose.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircleOutline
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.common.compose.base_components.BorderWidth
import com.posse.tanksrandomizer.common.compose.base_components.ButtonsShapeSmall
import com.posse.tanksrandomizer.common.compose.base_components.RandomizerText
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.MAX_ONLINE_SCREENS
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.models.ErrorResponse
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.models.PagedOnlineScreensEvent
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.models.PagedOnlineScreensState

@Composable
internal fun PagedOnlineScreensContent(
    runningAsOverlay: Boolean,
    onRedirectError: (ErrorResponse) -> Unit,
    state: PagedOnlineScreensState,
    onEvent: (PagedOnlineScreensEvent) -> Unit,
    modifier: Modifier,
) {
    val selectedTab by remember(state) {
        mutableStateOf(
            state.screens.find { screenData -> screenData.selected }?.position ?: 0
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .windowInsetsPadding(WindowInsets.safeContent.only(WindowInsetsSides.Top))
    ) {
        TabsRow(
            selectedTab = selectedTab,
            state = state,
            onTabSelected = { id -> onEvent(PagedOnlineScreensEvent.OnlineScreenSelected(id)) },
            onAddScreenPressed = { onEvent(PagedOnlineScreensEvent.AddScreenPressed) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        PagedNavigation(
            runningAsOverlay = runningAsOverlay,
            onRedirectError = onRedirectError,
            onSuccessLogin = { id, name, token ->
                onEvent(PagedOnlineScreensEvent.OnSuccessLogin(id, name, token))
            },
            state = state,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        )
    }
}

@Composable
private fun TabsRow(
    selectedTab: Int,
    state: PagedOnlineScreensState,
    onTabSelected: (id: String) -> Unit,
    onAddScreenPressed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    PrimaryScrollableTabRow(
        selectedTabIndex = selectedTab,
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.primary,
        divider = {},
        minTabWidth = ButtonDefaults.MinWidth,
        indicator = {
            Box(
                Modifier
                    .tabIndicatorOffset(
                        selectedTabIndex = selectedTab,
                        matchContentSize = true,
                    )
                    .requiredWidthIn(min = ButtonDefaults.MinWidth)
                    .fillMaxHeight()
                    .border(
                        width = BorderWidth,
                        color = MaterialTheme.colorScheme.primary,
                        shape = ButtonsShapeSmall,
                    )
            )
        },
        modifier = modifier
    ) {
        state.screens.forEach { screenData ->
            Tab(
                selected = screenData.selected,
                onClick = { onTabSelected(screenData.id) },
                text = {
                    RandomizerText(
                        text = screenData.name,
                        capitalize = false,
                        color = LocalContentColor.current,
                    )
                },
                modifier = Modifier.clip(ButtonsShapeSmall)
            )
        }

        if (state.screens.size < MAX_ONLINE_SCREENS) {
            Tab(
                selected = false,
                onClick = onAddScreenPressed,
                icon = {
                    Icon(
                        imageVector = Icons.Rounded.AddCircleOutline,
                        contentDescription = null,
                    )
                },
                modifier = Modifier.clip(ButtonsShapeSmall)
            )
        }
    }
}
