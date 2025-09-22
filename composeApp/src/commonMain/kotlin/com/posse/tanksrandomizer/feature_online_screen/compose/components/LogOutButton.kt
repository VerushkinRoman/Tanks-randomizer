package com.posse.tanksrandomizer.feature_online_screen.compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.lesta_logo
import tanks_randomizer.composeapp.generated.resources.logout

@Composable
fun LogOutButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val contentPadding = ButtonDefaults.TextButtonWithIconContentPadding
    val imageHeight = ButtonDefaults.MinHeight -
            contentPadding.calculateTopPadding() -
            contentPadding.calculateBottomPadding()

    Button(
        onClick = onClick,
        shape = RectangleShape,
        contentPadding = contentPadding,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
        ) {
            Image(
                painter = painterResource(Res.drawable.lesta_logo),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier.height(imageHeight),
            )

            Text(
                text = stringResource(Res.string.logout),
            )
        }
    }
}
