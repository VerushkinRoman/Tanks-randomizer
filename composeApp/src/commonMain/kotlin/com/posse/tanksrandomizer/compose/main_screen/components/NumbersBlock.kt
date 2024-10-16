package com.posse.tanksrandomizer.compose.main_screen.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import com.posse.tanksrandomizer.presentation.model.MainEvent
import com.posse.tanksrandomizer.presentation.model.MainState
import com.posse.tanksrandomizer.utils.BoxedInt
import org.jetbrains.compose.resources.painterResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.dice
import tanks_randomizer.composeapp.generated.resources.trash

@Composable
fun NumbersBlock(
    viewState: MainState,
    onEvent: (MainEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .border(1.dp, color = Color.DarkGray)
            .padding(6.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
            modifier = Modifier.horizontalScroll(rememberScrollState())
        ) {
            NumberButton(
                number = "-1",
                onClick = { onEvent(MainEvent.MinusPressed) }
            )

            NumberButton(
                number = "-10",
                onClick = { onEvent(MainEvent.MinusTenPressed) }
            )

            NumberButton(
                number = "-100",
                onClick = { onEvent(MainEvent.MinusHundredPressed) }
            )

            NumberItem(
                number = viewState.quantity,
                modifier = Modifier.padding(horizontal = 6.dp)
            )

            NumberButton(
                number = "+100",
                onClick = { onEvent(MainEvent.PlusHundredPressed) }
            )

            NumberButton(
                number = "+10",
                onClick = { onEvent(MainEvent.PlusTenPressed) }
            )

            NumberButton(
                number = "+1",
                onClick = { onEvent(MainEvent.PlusPressed) }
            )
        }

        Spacer(Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(Res.drawable.trash),
                contentDescription = null,
                modifier = Modifier
                    .height(ButtonDefaults.MinHeight)
                    .clickable { onEvent(MainEvent.TrashNumberPressed) }
            )

            NumberItem(
                boxedInt = viewState.generatedQuantity,
                modifier = Modifier
                    .padding(horizontal = 6.dp)
                    .border(1.dp, color = Color.Green)
            )

            Image(
                painter = painterResource(Res.drawable.dice),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.Gray),
                modifier = Modifier
                    .height(ButtonDefaults.MinHeight)
                    .background(Color.DarkGray.copy(alpha = 0.5f))
                    .clickable { onEvent(MainEvent.GenerateNumberPressed) }
                    .padding(4.dp)
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
            .background(Color.DarkGray.copy(alpha = 0.5f))
            .padding(horizontal = 4.dp),
    ) {
        Text(
            text = "999",
            color = Color.Transparent,
        )

        AnimatedContent(
            targetState = boxedInt
        ) {
            Text(
                text = it.int.toString(),
                color = Color.LightGray,
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
            .height(ButtonDefaults.MinHeight)
            .widthIn(min = ButtonDefaults.MinHeight)
            .background(Color.DarkGray.copy(alpha = 0.5f))
            .padding(horizontal = 4.dp),
    ) {
        Text(
            text = "999",
            color = Color.Transparent,
        )

        AnimatedContent(
            targetState = number
        ) {
            Text(
                text = it.toString(),
                color = Color.LightGray,
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
            .height(ButtonDefaults.MinHeight)
            .widthIn(min = ButtonDefaults.MinHeight)
            .background(Color.DarkGray.copy(alpha = 0.5f))
            .clickable(onClick = onClick)
            .padding(horizontal = 4.dp),
    ) {
        Text(
            text = number,
            color = Color.Gray,
        )
    }
}