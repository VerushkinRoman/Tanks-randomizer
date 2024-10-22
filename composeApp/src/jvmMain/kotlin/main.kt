import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.posse.tanksrandomizer.compose.App
import org.jetbrains.compose.resources.painterResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.app_icon
import java.awt.Dimension

fun main() = application {
    Window(
        title = "Tanks randomizer",
        state = rememberWindowState(width = 600.dp, height = 500.dp),
        onCloseRequest = ::exitApplication,
        icon = painterResource(Res.drawable.app_icon)
    ) {
        window.minimumSize = Dimension(
            with(LocalDensity.current) { (ButtonDefaults.MinHeight * 15).toPx().toInt() },
            500
        )
        App()
    }
}
