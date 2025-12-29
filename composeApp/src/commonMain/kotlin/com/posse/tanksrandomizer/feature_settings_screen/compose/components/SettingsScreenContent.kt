package com.posse.tanksrandomizer.feature_settings_screen.compose.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.common.compose.base_components.BorderWidth
import com.posse.tanksrandomizer.common.compose.base_components.CommonScreenColumn
import com.posse.tanksrandomizer.feature_settings_screen.presentation.model.SettingsEvent
import com.posse.tanksrandomizer.feature_settings_screen.presentation.model.SettingsState

@Composable
fun SettingsScreenContent(
    viewState: SettingsState,
    runningAsOverlay: Boolean,
    onEvent: (SettingsEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current

    val columnSpacer = 16.dp
    var additionalSpace by remember {
        mutableStateOf(0.dp)
    }

    val divider: @Composable (ColumnScope.() -> Unit) = {
        Spacer(Modifier.height(columnSpacer))

        HorizontalDivider(
            thickness = BorderWidth,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(Modifier.height(columnSpacer))
    }

    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = modifier,
    ) {
        CommonScreenColumn(
            runningAsOverlay = runningAsOverlay,
            modifier = Modifier.fillMaxSize(),
        ) {
            MultiaccountBlock(
                multiaccountEnabled = viewState.multiaccountEnabled,
                onMultiaccountChange = { onEvent(SettingsEvent.MultiaccountEnabled(it)) },
                modifier = Modifier.fillMaxWidth(),
            )

            divider()

            LocaleBlock(
                currentLocale = viewState.locale,
                onLocaleChange = { onEvent(SettingsEvent.ChangeLocale(it)) },
                modifier = Modifier.fillMaxWidth(),
            )

            if (rotationAvailable) {
                divider()

                RotationBlock(
                    screenRotation = viewState.screenRotation,
                    enabled = !runningAsOverlay,
                    onEvent = onEvent,
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            if (overlayAvailable) {
                divider()

                OverlayPermissionControl(
                    openOverlaySettings = { onEvent(SettingsEvent.OverlayButtonPressed) },
                    modifier = Modifier.fillMaxWidth(),
                )

                divider()

                FullScreenSwitch(
                    fullScreenModeEnabled = viewState.fullScreenMode,
                    onEvent = onEvent,
                    modifier = Modifier.fillMaxWidth(),
                )

                divider()

                FloatingButtonConfig(
                    state = viewState,
                    onEvent = onEvent,
                    columnSpacer = columnSpacer,
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            Spacer(Modifier.height(additionalSpace))
        }

        BannerAD(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .onSizeChanged { size ->
                    additionalSpace = with(density) { size.height.toDp() }
                },
        )
    }
}

@Composable
expect fun BannerAD(modifier: Modifier = Modifier)

expect val rotationAvailable: Boolean

expect val overlayAvailable: Boolean
