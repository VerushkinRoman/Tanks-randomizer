@file:Suppress("FunctionName", "unused")

import androidx.compose.ui.window.ComposeUIViewController
import com.posse.tanksrandomizer.common.background_task_scheduler.BackgroundTokenUpdateManager
import com.posse.tanksrandomizer.common.compose.CommonPlatformApp
import com.posse.tanksrandomizer.common.core.di.Inject
import com.posse.tanksrandomizer.common.core.platform.PlatformConfiguration
import com.posse.tanksrandomizer.common.core.platform.PlatformSDK
import com.posse.tanksrandomizer.navigation.compose.MainNavigation
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController = ComposeUIViewController {
    PlatformSDK.init(PlatformConfiguration())

    Inject.instance<BackgroundTokenUpdateManager>().initialize()

    CommonPlatformApp {
        MainNavigation()
    }
}