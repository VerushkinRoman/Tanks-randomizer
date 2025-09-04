package com.posse.tanksrandomizer.common.domain.repository

import com.posse.tanksrandomizer.common.data.datasource.OfflineDataSource
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Nation
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Tier
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Type

class CommonTanksRepositoryImpl(
    private val offlineDataSource: OfflineDataSource,
) : CommonTanksRepository {
    override fun getTiers(): List<Tier> = offlineDataSource.getProperties(Tier.defaultValues)
    override fun setTiers(tiers: List<Tier>) = offlineDataSource.setProperties(tiers)

    override fun getNations(): List<Nation> = offlineDataSource.getProperties(Nation.defaultValues)
    override fun setNations(nations: List<Nation>) = offlineDataSource.setProperties(nations)

    override fun getTypes(): List<Type> = offlineDataSource.getProperties(Type.defaultValues)
    override fun setTypes(types: List<Type>) = offlineDataSource.setProperties(types)
}
