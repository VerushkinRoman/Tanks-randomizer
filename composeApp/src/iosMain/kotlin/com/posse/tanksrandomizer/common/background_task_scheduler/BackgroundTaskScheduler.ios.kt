package com.posse.tanksrandomizer.common.background_task_scheduler

import com.posse.tanksrandomizer.common.core.di.Inject
import com.posse.tanksrandomizer.common.core.platform.PlatformConfiguration
import com.posse.tanksrandomizer.common.domain.utils.Dispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.getString
import platform.UserNotifications.UNMutableNotificationContent
import platform.UserNotifications.UNNotificationRequest
import platform.UserNotifications.UNTimeIntervalNotificationTrigger
import platform.UserNotifications.UNUserNotificationCenter
import tanks_randomizer.composeapp.generated.resources.Res
import tanks_randomizer.composeapp.generated.resources.background_task_body
import tanks_randomizer.composeapp.generated.resources.background_task_error
import tanks_randomizer.composeapp.generated.resources.background_task_title

actual class BackgroundTaskScheduler actual constructor(
    private val configuration: PlatformConfiguration
) {
    private val scope = CoroutineScope(Inject.instance<Dispatchers>().io + SupervisorJob())

    actual fun scheduleDailyTokenUpdate() {
        scheduleNotificationForTokenUpdate()
    }

    actual fun cancelDailyTokenUpdate() {
        UNUserNotificationCenter.currentNotificationCenter()
            .removePendingNotificationRequestsWithIdentifiers(
                identifiers = listOf(IDENTIFIER)
            )
    }

    private fun scheduleNotificationForTokenUpdate() {
        scope.launch {
            val content = UNMutableNotificationContent().apply {
                setTitle(getString(Res.string.background_task_title))
                setBody(getString(Res.string.background_task_body))
            }

            val trigger = UNTimeIntervalNotificationTrigger.triggerWithTimeInterval(
                timeInterval = (24 * 60 * 60).toDouble(),
                repeats = true
            )

            val request = UNNotificationRequest.requestWithIdentifier(
                identifier = IDENTIFIER,
                content = content,
                trigger = trigger
            )

            UNUserNotificationCenter.currentNotificationCenter()
                .addNotificationRequest(request) { error ->
                    scope.launch {
                        error?.let {
                            println("${getString(Res.string.background_task_error)}: $it")
                        }
                    }
                }
        }
    }

    companion object {
        private const val IDENTIFIER = "token_update"
    }
}
