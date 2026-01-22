package com.posse.tanksrandomizer.common.data.networking

import com.posse.tanksrandomizer.common.core.platform.PlatformConfiguration

actual class NetworkChecker actual constructor(configuration: PlatformConfiguration) {
    actual fun checkNetworkQuality() = NetworkQuality.EXCELLENT
    actual enum class NetworkQuality { POOR, GOOD, EXCELLENT }
}
