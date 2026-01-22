package com.posse.tanksrandomizer.common.background_task_scheduler

import com.posse.tanksrandomizer.common.core.platform.PlatformConfiguration

expect class BackgroundTaskScheduler(
    configuration: PlatformConfiguration
){
    fun scheduleDailyTokenUpdate()
    fun cancelDailyTokenUpdate()
}
