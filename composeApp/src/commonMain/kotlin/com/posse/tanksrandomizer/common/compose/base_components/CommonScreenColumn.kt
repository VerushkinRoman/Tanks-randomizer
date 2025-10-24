package com.posse.tanksrandomizer.common.compose.base_components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CommonScreenColumn(
    modifier: Modifier = Modifier,
    runningAsOverlay: Boolean,
    content: @Composable (ColumnScope.() -> Unit)
) {
    val density = LocalDensity.current
    var additionalSpace: Dp by remember { mutableStateOf(0.dp) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .windowInsetsPadding(WindowInsets.safeContent.only(WindowInsetsSides.Vertical))
            .then(
                if (runningAsOverlay && additionalSpace <= ButtonDefaults.MinHeight + 16.dp) {
                    Modifier.padding(horizontal = ButtonDefaults.MinHeight + 16.dp)
                } else Modifier
            )
            .padding(vertical = 8.dp)
    ) {
        Spacer(Modifier.weight(1f).onSizeChanged { size ->
            @Suppress("AssignedValueIsNeverRead")
            additionalSpace = with(density) { size.height.toDp() }
        })

        content()

        Spacer(Modifier.weight(1f))
    }
}
