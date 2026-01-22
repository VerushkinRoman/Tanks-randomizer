package com.posse.tanksrandomizer.common.background_task_scheduler.di

import com.posse.tanksrandomizer.common.background_task_scheduler.BackgroundTaskScheduler
import com.posse.tanksrandomizer.common.background_task_scheduler.BackgroundTokenUpdateManager
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val backgroundModule = DI.Module("BackgroundModule ") {
    bind<BackgroundTaskScheduler>() with singleton {
        BackgroundTaskScheduler(
            configuration = instance(),
        )
    }

    bind<BackgroundTokenUpdateManager>() with singleton {
        BackgroundTokenUpdateManager(
            backgroundTaskScheduler = instance(),
            tokenManager = instance(),
            dispatchers = instance(),
        )
    }
}
