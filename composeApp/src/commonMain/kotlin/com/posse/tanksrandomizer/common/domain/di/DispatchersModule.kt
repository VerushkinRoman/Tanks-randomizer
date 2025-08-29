package com.posse.tanksrandomizer.common.domain.di

import com.posse.tanksrandomizer.common.domain.utils.Dispatchers
import com.posse.tanksrandomizer.common.domain.utils.DispatchersImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

val dispatchersModule = DI.Module("DispatchersModule") {
    bind<Dispatchers>() with singleton {
        DispatchersImpl()
    }
}
