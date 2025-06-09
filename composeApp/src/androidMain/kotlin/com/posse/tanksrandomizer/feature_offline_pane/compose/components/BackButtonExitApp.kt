package com.posse.tanksrandomizer.feature_offline_pane.compose.components

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.posse.tanksrandomizer.common.compose.utils.findActivity
import org.jetbrains.compose.resources.stringResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.press_back_again_to_exit

@Composable
internal fun BackButtonExitApp(enabled: () -> Boolean = { true }) {
    val context = LocalContext.current

    context.findActivity()?.let { activity ->
        var lastTimeBackPressed = remember { 0L }

        var isBackShown = remember { false }

        val text = stringResource(Res.string.press_back_again_to_exit)

        BackHandler(enabled()) {
            if (System.currentTimeMillis() - lastTimeBackPressed < BACK_BUTTON_EXIT_DELAY && isBackShown) {
                activity.finishAndRemoveTask()
                return@BackHandler
            } else {
                isBackShown = false
            }

            Toast
                .makeText(
                    context,
                    text,
                    Toast.LENGTH_SHORT
                )
                .show()

            isBackShown = true
            lastTimeBackPressed = System.currentTimeMillis()
        }
    }
}

private const val BACK_BUTTON_EXIT_DELAY = 3000