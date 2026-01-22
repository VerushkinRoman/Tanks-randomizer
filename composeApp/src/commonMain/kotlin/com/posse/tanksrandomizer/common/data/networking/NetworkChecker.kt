package com.posse.tanksrandomizer.common.data.networking

import com.posse.tanksrandomizer.common.core.platform.PlatformConfiguration

expect class NetworkChecker(
    configuration: PlatformConfiguration,
) {
    enum class NetworkQuality {
        POOR,
        GOOD,
        EXCELLENT
    }

    fun checkNetworkQuality(): NetworkQuality
}
