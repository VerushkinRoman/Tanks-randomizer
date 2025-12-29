package com.posse.tanksrandomizer.common.paged_screens_navigation.compose.components

import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircleOutline
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.common.compose.base_components.BorderWidth
import com.posse.tanksrandomizer.common.compose.base_components.ButtonsShapeSmall
import com.posse.tanksrandomizer.common.compose.base_components.RandomizerText
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models.PagedScreensEvent
import com.posse.tanksrandomizer.common.paged_screens_navigation.presentation.models.PagedScreensState
import com.posse.tanksrandomizer.common.paged_screens_navigation.utils.MAX_ONLINE_SCREENS
import com.posse.tanksrandomizer.feature_settings_screen.domain.repository.SettingsRepository
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.kodein.di.compose.rememberInstance
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.navigation_menu_delete
import tanks_randomizer.composeapp.generated.resources.navigation_menu_left
import tanks_randomizer.composeapp.generated.resources.navigation_menu_right
import tanks_randomizer.composeapp.generated.resources.new_window
import tanks_randomizer.composeapp.generated.resources.trash

@Composable
internal fun PagedScreensContent(
    state: PagedScreensState,
    selectedTab: Int,
    onEvent: (PagedScreensEvent) -> Unit,
    pagedScreen: @Composable (screenId: String) -> Unit,
    modifier: Modifier,
) {
    var previousSelectedItem by remember { mutableIntStateOf(selectedTab) }
    val settingsRepository by rememberInstance<SettingsRepository>()
    var multiaccountEnabled by remember { mutableStateOf(true) }

    LaunchedEffect(settingsRepository) {
        settingsRepository.getMultiaccountEnabled().collect { enabled ->
            multiaccountEnabled = enabled
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .windowInsetsPadding(WindowInsets.safeContent.only(WindowInsetsSides.Top))
    ) {
        if (multiaccountEnabled) {
            Spacer(Modifier.height(4.dp))

            TabsRow(
                selectedTab = selectedTab,
                state = state,
                onEvent = onEvent,
                onItemClick = {
                    previousSelectedItem = selectedTab
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))
        }

        PagedNavigation(
            state = state,
            selectedOrder = selectedTab,
            previousSelectedOrder = previousSelectedItem,
            pagedScreen = pagedScreen,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun TabsRow(
    selectedTab: Int,
    state: PagedScreensState,
    onEvent: (PagedScreensEvent) -> Unit,
    onItemClick: () -> Unit,
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
                    .border(
                        width = BorderWidth,
                        color = MaterialTheme.colorScheme.primary,
                        shape = ButtonsShapeSmall,
                    )
                    .extraWidth(30.dp)
                    .fillMaxHeight()
            )
        },
        modifier = modifier
    ) {
        state.screens.forEach { screenData ->
            var expanded by remember(screenData.metadata.id) { mutableStateOf(false) }

            Box(
                contentAlignment = Alignment.Center
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .requiredHeightIn(min = 48.dp)
                        .requiredWidthIn(min = ButtonDefaults.MinWidth)
                        .clip(ButtonsShapeSmall)
                        .combinedClickable(
                            onClick = {
                                onItemClick()
                                onEvent(PagedScreensEvent.ScreenSelected(screenData.metadata.id))
                            },
                            onLongClick = { expanded = true }
                        )
                        .padding(horizontal = 8.dp)
                ) {
                    RandomizerText(
                        text = screenData.metadata.name ?: stringResource(Res.string.new_window),
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
                                color = if (screenData.metadata.position > 0) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.onPrimaryContainer,
                            )
                        },
                        onClick = {
                            onItemClick()
                            onEvent(PagedScreensEvent.MoveLeft(screenData.metadata.id))
                        },
                        leadingIcon = {
                            @Suppress("DEPRECATION")
                            Icon(
                                imageVector = Icons.Rounded.ArrowBack,
                                contentDescription = null,
                                modifier = Modifier.sizeIn(
                                    maxWidth = IconButtonDefaults.largeIconSize,
                                    maxHeight = IconButtonDefaults.largeIconSize,
                                ),
                            )
                        },
                        enabled = screenData.metadata.position > 0,
                    )
                    DropdownMenuItem(
                        text = {
                            RandomizerText(
                                text = stringResource(Res.string.navigation_menu_right),
                                color = if (screenData.metadata.position < state.screens.size - 1) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.onPrimaryContainer,
                            )
                        },
                        onClick = {
                            onItemClick()
                            onEvent(PagedScreensEvent.MoveRight(screenData.metadata.id))
                        },
                        leadingIcon = {
                            @Suppress("DEPRECATION")
                            Icon(
                                imageVector = Icons.Rounded.ArrowForward,
                                contentDescription = null,
                                modifier = Modifier.sizeIn(
                                    maxWidth = IconButtonDefaults.largeIconSize,
                                    maxHeight = IconButtonDefaults.largeIconSize,
                                ),
                            )
                        },
                        enabled = screenData.metadata.position < state.screens.size - 1,
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
                            onItemClick()
                            onEvent(PagedScreensEvent.RemoveScreenPressed(screenData.metadata.id))
                        },
                        leadingIcon = {
                            Icon(
                                painter = painterResource(Res.drawable.trash),
                                contentDescription = null,
                                modifier = Modifier.sizeIn(
                                    maxWidth = IconButtonDefaults.largeIconSize,
                                    maxHeight = IconButtonDefaults.largeIconSize,
                                ),
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
                onClick = {
                    onItemClick()
                    onEvent(PagedScreensEvent.AddScreenPressed)
                },
                icon = {
                    Icon(
                        imageVector = Icons.Rounded.AddCircleOutline,
                        contentDescription = null,
                        modifier = Modifier.sizeIn(
                            maxWidth = IconButtonDefaults.largeIconSize,
                            maxHeight = IconButtonDefaults.largeIconSize,
                        ),
                    )
                },
                modifier = Modifier.clip(ButtonsShapeSmall)
            )
        }
    }
}

private fun Modifier.extraWidth(extra: Dp) = this.layout { measurable, constraints ->
    val placeable = measurable.measure(constraints)
    val extraPx = extra.roundToPx()
    val newWidth = placeable.width + extraPx

    layout(newWidth, placeable.height) {
        placeable.placeRelative(0, 0)
    }
}
