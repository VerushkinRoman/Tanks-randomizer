package com.posse.tanksrandomizer.feature_online_screen.compose.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.online_tanks_filtered
import tanks_randomizer.composeapp.generated.resources.online_tanks_refresh_time
import tanks_randomizer.composeapp.generated.resources.online_tanks_total
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Composable
fun getTotalText(tanksOverall: Int): String {
    return stringResource(Res.string.online_tanks_total) + ": " + tanksOverall
}

@OptIn(FormatStringsInDatetimeFormats::class, ExperimentalTime::class)
@Composable
fun getRefreshText(lastAccountUpdated: Instant?): String {
    val timeText = remember(lastAccountUpdated) {
        val dateTime = lastAccountUpdated?.let {
            it.toLocalDateTime(TimeZone.currentSystemDefault()).format(
                LocalDateTime.Format {
                    byUnicodePattern("HH:mm:ss dd.MM.yyyy")
                }
            )
        } ?: "-"

        mutableStateOf(dateTime)
    }

    return stringResource(Res.string.online_tanks_refresh_time) + ": " + timeText.value
}

@Composable
fun getFilteredText(tanksByFilter: Int): String {
    return stringResource(Res.string.online_tanks_filtered) + ": " + tanksByFilter
}
