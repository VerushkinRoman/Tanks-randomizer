package com.posse.tanksrandomizer.feature_online_navigation.navigation.compose.components

import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircleOutline
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.common.compose.base_components.BorderWidth
import com.posse.tanksrandomizer.common.compose.base_components.ButtonsShapeSmall
import com.posse.tanksrandomizer.common.compose.base_components.RandomizerText
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.MAX_ONLINE_SCREENS
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.models.ErrorResponse
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.models.PagedOnlineScreensEvent
import com.posse.tanksrandomizer.feature_online_navigation.navigation.presentation.models.PagedOnlineScreensState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.navigation_menu_delete
import tanks_randomizer.composeapp.generated.resources.navigation_menu_left
import tanks_randomizer.composeapp.generated.resources.navigation_menu_right
import tanks_randomizer.composeapp.generated.resources.trash

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
            onEvent = onEvent,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TabsRow(
    selectedTab: Int,
    state: PagedOnlineScreensState,
    onEvent: (PagedOnlineScreensEvent) -> Unit,
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
            var expanded by remember(screenData.id) { mutableStateOf(false) }

            Box(
                contentAlignment = Alignment.Center
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .requiredWidthIn(min = ButtonDefaults.MinWidth)
                        .clip(ButtonsShapeSmall)
                        .combinedClickable(
                            onClick = {
                                onEvent(PagedOnlineScreensEvent.OnlineScreenSelected(screenData.id))
                            },
                            onLongClick = { expanded = true }
                        )
                        .padding(horizontal = 4.dp)
                ) {
                    RandomizerText(
                        text = screenData.name,
                        capitalize = false,
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    offset = DpOffset(x = 0.dp, y = 4.dp),
                    shape = ButtonsShapeSmall,
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                ) {
                    DropdownMenuItem(
                        text = {
                            RandomizerText(
                                text = stringResource(Res.string.navigation_menu_left),
                                color = if (screenData.position > 0) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.onPrimaryContainer,
                            )
                        },
                        onClick = {
                            onEvent(PagedOnlineScreensEvent.MoveLeft(screenData.id))
                        },
                        leadingIcon = {
                            @Suppress("DEPRECATION")
                            Icon(
                                imageVector = Icons.Rounded.ArrowBack,
                                contentDescription = null
                            )
                        },
                        enabled = screenData.position > 0,
                    )
                    DropdownMenuItem(
                        text = {
                            RandomizerText(
                                text = stringResource(Res.string.navigation_menu_right),
                                color = if (screenData.position < state.screens.size - 1) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.onPrimaryContainer,
                            )
                        },
                        onClick = {
                            onEvent(PagedOnlineScreensEvent.MoveRight(screenData.id))
                        },
                        leadingIcon = {
                            @Suppress("DEPRECATION")
                            Icon(
                                imageVector = Icons.Rounded.ArrowForward,
                                contentDescription = null
                            )
                        },
                        enabled = screenData.position < state.screens.size - 1,
                    )

                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )

                    DropdownMenuItem(
                        text = {
                            RandomizerText(
                                text = stringResource(Res.string.navigation_menu_delete),
                                color = if (state.screens.size > 1) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.onPrimaryContainer,
                            )
                        },
                        onClick = {
                            onEvent(PagedOnlineScreensEvent.RemoveScreenPressed(screenData.id))
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(Res.drawable.trash),
                                contentDescription = null
                            )
                        },
                        enabled = state.screens.size > 1,
                    )
                }
            }
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
