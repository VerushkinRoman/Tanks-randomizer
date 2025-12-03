package com.posse.tanksrandomizer.feature_online_navigation.common.domain.di

import com.posse.tanksrandomizer.feature_online_navigation.common.domain.repository.PagedOnlineScreenRepository
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.repository.PagedOnlineScreenRepositoryImpl
import org.kodein.di.DI
import org.kodein.di.bindProvider
import org.kodein.di.instance

val pagedOnlineScreenRepositoryModule = DI.Module("PagedOnlineScreenRepositoryModule") {
    bindProvider<PagedOnlineScreenRepository> {
        PagedOnlineScreenRepositoryImpl(
            dataSource = instance()
        )
    }
}
