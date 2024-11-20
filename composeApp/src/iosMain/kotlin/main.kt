import androidx.compose.ui.window.ComposeUIViewController
import com.posse.tanksrandomizer.ComposeApp
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController { ComposeApp() }
