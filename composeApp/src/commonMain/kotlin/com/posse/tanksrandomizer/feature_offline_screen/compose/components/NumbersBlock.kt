package com.posse.tanksrandomizer.feature_offline_screen.compose.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.posse.tanksrandomizer.common.compose.base_components.BigButtonWithImage
import com.posse.tanksrandomizer.common.compose.base_components.BorderWidth
import com.posse.tanksrandomizer.common.compose.base_components.ButtonsShapeLarge
import com.posse.tanksrandomizer.common.compose.base_components.ButtonsShapeSmall
import com.posse.tanksrandomizer.common.compose.utils.LocalElementSize
import com.posse.tanksrandomizer.common.compose.utils.invisibleModifier
import com.posse.tanksrandomizer.common.domain.utils.BoxedInt
import com.posse.tanksrandomizer.feature_offline_screen.presentation.models.Numbers
import com.posse.tanksrandomizer.feature_offline_screen.presentation.models.OfflineScreenEvent
import com.posse.tanksrandomizer.feature_offline_screen.presentation.models.OfflineScreenEvent.QuantityChanged
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.dice
import tanks_randomizer.composeapp.generated.resources.trash

@Composable
internal fun NumbersBlock(
    numbers: Numbers,
    onEvent: (OfflineScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                .padding(4.dp)
                .horizontalScroll(rememberScrollState())
        ) {
            NumberButton(
                number = "-1",
                onClick = { onEvent(QuantityChanged(-1)) }
            )

            NumberButton(
                number = "-10",
                onClick = { onEvent(QuantityChanged(-10)) }
            )

            NumberButton(
                number = "-100",
                onClick = { onEvent(QuantityChanged(-100)) }
            )

            NumberItem(
                number = numbers.quantity,
                modifier = Modifier.padding(horizontal = 6.dp)
            )

            NumberButton(
                number = "+100",
                onClick = { onEvent(QuantityChanged(100)) }
            )

            NumberButton(
                number = "+10",
                onClick = { onEvent(QuantityChanged(10)) }
            )

            NumberButton(
                number = "+1",
                onClick = { onEvent(QuantityChanged(1)) }
            )
        }

        Spacer(Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BigButtonWithImage(
                painter = painterResource(Res.drawable.trash),
                contentDescription = stringResource(Res.string.trash),
                onClick = { onEvent(OfflineScreenEvent.TrashNumberPressed) }
            )

            NumberItem(
                boxedInt = numbers.generatedQuantity,
                modifier = Modifier.padding(horizontal = 6.dp)
            )

            BigButtonWithImage(
                painter = painterResource(Res.drawable.dice),
                contentDescription = stringResource(Res.string.dice),
                onClick = { onEvent(OfflineScreenEvent.GenerateNumberPressed) }
            )
        }
    }
}

@Composable
private fun NumberItem(
    boxedInt: BoxedInt,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .height(ButtonDefaults.MinHeight)
            .widthIn(min = ButtonDefaults.MinHeight)
            .border(
                width = BorderWidth,
                color = Color.Green,
                shape = ButtonsShapeLarge
            )
            .clip(ButtonsShapeLarge)
            .padding(horizontal = 4.dp)
    ) {
        Text(
            text = "999",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Black,
            modifier = invisibleModifier,
        )

        AnimatedContent(
            targetState = boxedInt
        ) {
            Text(
                text = it.int.toString(),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Black,
            )
        }
    }
}

@Composable
private fun NumberItem(
    number: Int,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .height(LocalElementSize.current)
            .widthIn(min = LocalElementSize.current)
            .border(
                width = BorderWidth,
                color = MaterialTheme.colorScheme.onSurface,
                shape = ButtonsShapeSmall
            )
            .clip(ButtonsShapeSmall)
            .padding(horizontal = 4.dp)
    ) {
        Text(
            text = "999",
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Black,
            modifier = invisibleModifier,
        )

        AnimatedContent(
            targetState = number
        ) {
            Text(
                text = it.toString(),
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Black,
            )
        }
    }
}

@Composable
private fun NumberButton(
    number: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .height(LocalElementSize.current)
            .widthIn(min = LocalElementSize.current)
            .clickable(onClick = onClick)
            .border(
                width = BorderWidth,
                color = MaterialTheme.colorScheme.onPrimary,
                shape = ButtonsShapeSmall
            )
            .clip(ButtonsShapeSmall)
            .padding(horizontal = 4.dp)
    ) {
        Text(
            text = number,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodySmall,
            fontSize = 10.sp,
            fontWeight = FontWeight.Black,
        )
    }
}
