package com.posse.tanksrandomizer.navigation.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.posse.tanksrandomizer.CommonPlatformApp
import com.posse.tanksrandomizer.common.compose.utils.LocalMaxHeight
import com.posse.tanksrandomizer.common.compose.utils.LocalMaxWidth

@Composable
internal fun AndroidApp(
    exitApp: () -> Unit = {},
    @SuppressLint("ModifierParameter") modifier: Modifier = Modifier
) {
    CommonPlatformApp {
        BoxWithConstraints {
            CompositionLocalProvider(
                LocalMaxWidth provides maxWidth,
                LocalMaxHeight provides maxHeight,
            ) {
                Navigation(
                    exitApp = exitApp,
                    modifier = modifier
                )
            }
        }
    }
}