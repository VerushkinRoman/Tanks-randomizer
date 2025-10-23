package com.posse.tanksrandomizer.common.data.di

import android.util.Log
import io.ktor.client.plugins.logging.Logger

actual fun getKtorLogger(): Logger {
    return object : Logger {
        override fun log(message: String) {
            Log.d("Ktor", message)
        }
    }
}
