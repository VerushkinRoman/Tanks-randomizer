package com.posse.tanksrandomizer.common.domain.di

import com.posse.tanksrandomizer.common.domain.models.DataSourceFor
import com.posse.tanksrandomizer.common.domain.repository.ADRepository
import com.posse.tanksrandomizer.common.domain.repository.ADRepositoryImpl
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

val adModule = DI.Module("ADModule") {
    bindProvider<ADRepository> {
        ADRepositoryImpl(dataSource = instance(tag = DataSourceFor.Common))
    }
}
