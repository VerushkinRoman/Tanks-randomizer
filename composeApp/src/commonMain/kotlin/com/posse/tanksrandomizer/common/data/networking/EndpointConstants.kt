package com.posse.tanksrandomizer.common.data.networking

@Suppress("SpellCheckingInspection")
object EndpointConstants {
    const val MAX_REQUESTS_PER_SECONDS = 10
    const val MAX_PARAMETERS_PER_REQUEST = 100
    const val AUTH_PATH = "https://api.tanki.su/wot/auth/"
    const val BLITZ_PATH = "https://papi.tanksblitz.ru/wotb/"
    const val REDIRECT_URL = "http://tanks.randomizer/"
    const val REDIRECT_URL_ENCODED = "http%253A%252F%252Ftanks.randomizer%252F"
}
