import androidx.compose.ui.window.ComposeUIViewController
import com.posse.tanksrandomizer.compose.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController { App() }
