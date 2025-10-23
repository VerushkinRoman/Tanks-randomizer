package com.posse.tanksrandomizer.common.data.di

import io.ktor.client.plugins.logging.Logger

actual fun getKtorLogger(): Logger {
    return commonLogger
}
