import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.multiplatform.webview.util.addTempDirectoryRemovalHook
import com.posse.tanksrandomizer.common.background_task_scheduler.BackgroundTokenUpdateManager
import com.posse.tanksrandomizer.common.compose.CommonPlatformApp
import com.posse.tanksrandomizer.common.core.di.Inject
import com.posse.tanksrandomizer.common.core.platform.PlatformConfiguration
import com.posse.tanksrandomizer.common.core.platform.PlatformSDK
import com.posse.tanksrandomizer.feature_settings_screen.presentation.interactor.SettingsInteractor
import com.posse.tanksrandomizer.navigation.compose.MainNavigation
import dev.datlag.kcef.KCEF
import org.jetbrains.compose.resources.painterResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.app_icon
import java.awt.Dimension
import java.awt.event.ComponentEvent
import java.awt.event.ComponentListener

fun main() = application {
    PlatformSDK.init(PlatformConfiguration())
    Inject.instance<BackgroundTokenUpdateManager>().initialize()
    addTempDirectoryRemovalHook()
    val settingsInteractor: SettingsInteractor = Inject.instance()
    val size = settingsInteractor.getDesktopWindowSize()
    val density = LocalDensity.current
    val initialWidth = with(density) { size?.first?.toDp() }
    val initialHeight = with(density) { size?.second?.toDp() }

    AppWindow(
        settingsInteractor = settingsInteractor,
        initialWidth = initialWidth,
        initialHeight = initialHeight,
        exitApplication = ::exitApplication,
    )
}

@Composable
private fun AppWindow(
    settingsInteractor: SettingsInteractor,
    initialWidth: Dp?,
    initialHeight: Dp?,
    exitApplication: () -> Unit
) {
    Window(
        title = "Random Tank Generator",
        state = rememberWindowState(
            width = initialWidth ?: 617.dp,
            height = initialHeight ?: 470.dp,
            position = WindowPosition.Aligned(Alignment.Center)
        ),
        onCloseRequest = exitApplication,
        icon = painterResource(Res.drawable.app_icon)
    ) {
        window.minimumSize = Dimension(
            with(LocalDensity.current) { (ButtonDefaults.MinHeight * 11).toPx().toInt() },
            400
        )

        DisposableEffect(true) {
            val componentListener = object : ComponentListener {
                override fun componentResized(e: ComponentEvent?) {
                    e?.let { event ->
                        settingsInteractor.setDesktopWindowSize(
                            Pair(
                                event.component.width,
                                event.component.height
                            )
                        )
                    }
                }

                override fun componentMoved(e: ComponentEvent?) = Unit
                override fun componentShown(e: ComponentEvent?) = Unit
                override fun componentHidden(e: ComponentEvent?) = Unit
            }

            window.addComponentListener(componentListener)

            onDispose {
                window.removeComponentListener(componentListener)
            }
        }

        CommonPlatformApp {
            MainNavigation(
                modifier = Modifier.fillMaxSize()
            )
        }

        DisposableEffect(Unit) {
            onDispose {
                KCEF.disposeBlocking()
            }
        }
    }
}
