import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.posse.tanksrandomizer.CommonPlatformApp
import com.posse.tanksrandomizer.common.core.platform.PlatformConfiguration
import com.posse.tanksrandomizer.common.core.platform.PlatformSDK
import com.posse.tanksrandomizer.navigation.compose.MainScaffoldWithBottomSheet
import org.jetbrains.compose.resources.painterResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.app_icon
import java.awt.Dimension

fun main() = application {
    PlatformSDK.init(PlatformConfiguration())

    Window(
        title = "Random Tank Generator",
        state = rememberWindowState(width = 600.dp, height = 550.dp),
        onCloseRequest = ::exitApplication,
        icon = painterResource(Res.drawable.app_icon)
    ) {
        window.minimumSize = Dimension(
            with(LocalDensity.current) { (ButtonDefaults.MinHeight * 16).toPx().toInt() },
            550
        )

        CommonPlatformApp {
            MainScaffoldWithBottomSheet()
        }
    }
}
