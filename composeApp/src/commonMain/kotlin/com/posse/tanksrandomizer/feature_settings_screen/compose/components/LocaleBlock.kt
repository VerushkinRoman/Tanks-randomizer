package com.posse.tanksrandomizer.feature_settings_screen.compose.components

import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.posse.tanksrandomizer.common.compose.base_components.RandomizerText
import com.posse.tanksrandomizer.common.compose.theme.themedSegmentedButtonColors
import com.posse.tanksrandomizer.feature_settings_screen.compose.components.common.CommonSettingsActionBlock
import com.posse.tanksrandomizer.feature_settings_screen.compose.components.common.CommonSettingsActionTitle
import com.posse.tanksrandomizer.feature_settings_screen.domain.models.AppLocale
import org.jetbrains.compose.resources.stringResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.settings_language_title

@Composable
internal fun LocaleBlock(
    currentLocale: AppLocale,
    onLocaleChange: (AppLocale) -> Unit,
    modifier: Modifier = Modifier,
) {
    CommonSettingsActionBlock(
        caption = { centered ->
            CommonSettingsActionTitle(
                centered = centered,
                title = stringResource(Res.string.settings_language_title),
            )
        },
        action = {
            LanguageButtons(
                currentLocale = currentLocale,
                onLocaleChange = onLocaleChange,
            )
        },
        modifier = modifier
    )
}

@Composable
private fun LanguageButtons(
    currentLocale: AppLocale,
    onLocaleChange: (AppLocale) -> Unit,
    modifier: Modifier = Modifier,
) {
    SingleChoiceSegmentedButtonRow(
        modifier = modifier,
    ) {
        SegmentedButton(
            shape = SegmentedButtonDefaults.itemShape(
                index = 0,
                count = 2,
            ),
            onClick = { onLocaleChange(AppLocale.Ru) },
            selected = currentLocale == AppLocale.Ru,
            colors = themedSegmentedButtonColors(),
            label = {
                RandomizerText(
                    text = "Рус",
                    fontSize = 14.sp,
                    capitalize = false
                )
            },
        )

        SegmentedButton(
            shape = SegmentedButtonDefaults.itemShape(
                index = 1,
                count = 2,
            ),
            onClick = { onLocaleChange(AppLocale.En) },
            selected = currentLocale == AppLocale.En,
            colors = themedSegmentedButtonColors(),
            label = {
                RandomizerText(
                    text = "Eng",
                    fontSize = 14.sp,
                    capitalize = false
                )
            },
        )
    }
}
