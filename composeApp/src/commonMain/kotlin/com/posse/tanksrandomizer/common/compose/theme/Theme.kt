package com.posse.tanksrandomizer.common.compose.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val lightScheme = lightColorScheme(
    primary = primaryLight,
//    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
//    secondary = secondaryLight,
//    onSecondary = onSecondaryLight,
//    secondaryContainer = secondaryContainerLight,
//    onSecondaryContainer = onSecondaryContainerLight,
//    tertiary = tertiaryLight,
//    onTertiary = onTertiaryLight,
//    tertiaryContainer = tertiaryContainerLight,
//    onTertiaryContainer = onTertiaryContainerLight,
//    error = errorLight,
//    onError = onErrorLight,
//    errorContainer = errorContainerLight,
//    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
//    onBackground = onBackgroundLight,
//    surface = surfaceLight,
//    onSurface = onSurfaceLight,
//    surfaceVariant = surfaceVariantLight,
//    onSurfaceVariant = onSurfaceVariantLight,
//    outline = outlineLight,
//    outlineVariant = outlineVariantLight,
//    scrim = scrimLight,
//    inverseSurface = inverseSurfaceLight,
//    inverseOnSurface = inverseOnSurfaceLight,
//    inversePrimary = inversePrimaryLight,
//    surfaceDim = surfaceDimLight,
//    surfaceBright = surfaceBrightLight,
//    surfaceContainerLowest = surfaceContainerLowestLight,
//    surfaceContainerLow = surfaceContainerLowLight,
//    surfaceContainer = surfaceContainerLight,
//    surfaceContainerHigh = surfaceContainerHighLight,
//    surfaceContainerHighest = surfaceContainerHighestLight,
)

private val darkScheme = darkColorScheme(
    primary = primaryDark,
//    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
//    secondary = secondaryDark,
//    onSecondary = onSecondaryDark,
//    secondaryContainer = secondaryContainerDark,
//    onSecondaryContainer = onSecondaryContainerDark,
//    tertiary = tertiaryDark,
//    onTertiary = onTertiaryDark,
//    tertiaryContainer = tertiaryContainerDark,
//    onTertiaryContainer = onTertiaryContainerDark,
//    error = errorDark,
//    onError = onErrorDark,
//    errorContainer = errorContainerDark,
//    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
//    onBackground = onBackgroundDark,
//    surface = surfaceDark,
//    onSurface = onSurfaceDark,
//    surfaceVariant = surfaceVariantDark,
//    onSurfaceVariant = onSurfaceVariantDark,
//    outline = outlineDark,
//    outlineVariant = outlineVariantDark,
//    scrim = scrimDark,
//    inverseSurface = inverseSurfaceDark,
//    inverseOnSurface = inverseOnSurfaceDark,
//    inversePrimary = inversePrimaryDark,
//    surfaceDim = surfaceDimDark,
//    surfaceBright = surfaceBrightDark,
//    surfaceContainerLowest = surfaceContainerLowestDark,
//    surfaceContainerLow = surfaceContainerLowDark,
//    surfaceContainer = surfaceContainerDark,
//    surfaceContainerHigh = surfaceContainerHighDark,
//    surfaceContainerHighest = surfaceContainerHighestDark,
)

class TankTypesColors(
    val common: Color = tankTypeCommonColor,
    val premium: Color = tankTypePremiumColor,
    val premiumized: Color = tankTypePremiumizedColor,
    val collection: Color = tankTypeCollectionColor,
)

@Suppress("UnusedReceiverParameter")
val ColorScheme.tankTypesColors: TankTypesColors
    get() = TankTypesColors()

@Composable
fun AppTheme(
//    darkTheme: Boolean = isSystemInDarkTheme(),
    darkTheme: Boolean = true,
    // Dynamic color is available on Android 12+
//    dynamicColor: Boolean = true,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    SystemAppearance(!darkTheme)

    val colorScheme = dynamicColorScheme(darkTheme, dynamicColor)
        ?: when {
            darkTheme -> darkScheme
            else -> lightScheme
        }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}

@Composable
internal expect fun SystemAppearance(isDark: Boolean)

@Composable
internal expect fun dynamicColorScheme(darkTheme: Boolean, dynamicColor: Boolean): ColorScheme?

@Composable
fun themedSegmentedButtonColors() = SegmentedButtonDefaults.colors(
    activeContainerColor = MaterialTheme.colorScheme.primaryContainer,
    activeContentColor = MaterialTheme.colorScheme.primary,
    activeBorderColor = MaterialTheme.colorScheme.primary,
    inactiveContainerColor = MaterialTheme.colorScheme.primaryContainer,
    inactiveContentColor = MaterialTheme.colorScheme.primary,
    inactiveBorderColor = MaterialTheme.colorScheme.primary,
    disabledActiveContainerColor = MaterialTheme.colorScheme.primaryContainer,
    disabledActiveContentColor = MaterialTheme.colorScheme.primary,
    disabledActiveBorderColor = MaterialTheme.colorScheme.primary,
    disabledInactiveContainerColor = MaterialTheme.colorScheme.primaryContainer,
    disabledInactiveContentColor = MaterialTheme.colorScheme.primary,
    disabledInactiveBorderColor = MaterialTheme.colorScheme.primary,
)
