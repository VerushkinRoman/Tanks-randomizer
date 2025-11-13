package com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.common.compose.base_components.ButtonsShapeSmall
import com.posse.tanksrandomizer.common.compose.base_components.RandomizerText
import com.posse.tanksrandomizer.common.compose.base_components.SmallButtonWithTextAndImage
import org.jetbrains.compose.resources.stringResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.online_dialog_confirm
import tanks_randomizer.composeapp.generated.resources.online_dialog_dismiss
import tanks_randomizer.composeapp.generated.resources.online_dialog_message

@Composable
fun LogoutDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.7f))
            .clickable(
                interactionSource = null,
                indication = null,
                onClick = onDismiss
            )
    ) {
        Card(
            shape = ButtonsShapeSmall,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                RandomizerText(
                    text = stringResource(Res.string.online_dialog_message)
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    SmallButtonWithTextAndImage(
                        text = stringResource(Res.string.online_dialog_dismiss),
                        onClick = onDismiss
                    )

                    SmallButtonWithTextAndImage(
                        text = stringResource(Res.string.online_dialog_confirm),
                        onClick = onConfirm
                    )
                }
            }
        }
    }
}
