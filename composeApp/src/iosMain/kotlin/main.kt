@file:Suppress("FunctionName", "unused")

import androidx.compose.ui.window.ComposeUIViewController
import com.posse.tanksrandomizer.CommonPlatformApp
import com.posse.tanksrandomizer.common.core.platform.PlatformConfiguration
import com.posse.tanksrandomizer.common.core.platform.PlatformSDK
import com.posse.tanksrandomizer.navigation.compose.MainNavigation
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController {
    PlatformSDK.init(PlatformConfiguration())

    CommonPlatformApp {
        MainNavigation()
    }
}