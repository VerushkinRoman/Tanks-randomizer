package com.posse.tanksrandomizer.common.data.networking

import android.Manifest
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.annotation.RequiresPermission
import androidx.core.content.getSystemService
import com.posse.tanksrandomizer.common.core.platform.PlatformConfiguration

actual class NetworkChecker actual constructor(
    private val configuration: PlatformConfiguration
) {
    actual enum class NetworkQuality { POOR, GOOD, EXCELLENT }

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    actual fun checkNetworkQuality(): NetworkQuality {
        val context = configuration.androidContext

        val connectivityManager = context.getSystemService<ConnectivityManager>()

        val network = connectivityManager?.activeNetwork ?: return NetworkQuality.POOR
        val caps = connectivityManager.getNetworkCapabilities(network) ?: return NetworkQuality.POOR

        return when {
            caps.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ->
                NetworkQuality.EXCELLENT

            caps.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                when {
                    caps.linkDownstreamBandwidthKbps >= 1024 -> NetworkQuality.GOOD
                    caps.linkDownstreamBandwidthKbps >= 512 -> NetworkQuality.GOOD
                    else -> NetworkQuality.POOR
                }
            }

            else -> NetworkQuality.POOR
        }
    }
}