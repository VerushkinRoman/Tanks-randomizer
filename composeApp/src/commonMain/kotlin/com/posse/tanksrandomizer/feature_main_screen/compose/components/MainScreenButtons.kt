package com.posse.tanksrandomizer.feature_main_screen.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.common.compose.base_components.ButtonsShapeLarge
import org.jetbrains.compose.resources.stringResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.login_offline

@Composable
internal fun MainScreenButtons(
    loading: Boolean,
    onLoginClick: () -> Unit,
    onOfflineClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.width(IntrinsicSize.Max),
    ) {
        MainScreenLogInButton(
            loading = loading,
            onClick = onLoginClick,
            modifier = Modifier.fillMaxWidth()
        )

        TextButton(
            onClick = onOfflineClick,
            shape = ButtonsShapeLarge,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = stringResource(Res.string.login_offline).uppercase(),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Black,
            )
        }
    }
}
