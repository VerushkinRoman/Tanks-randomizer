package com.posse.tanksrandomizer.common.domain.repository

import com.posse.tanksrandomizer.common.data.datasource.OfflineDataSource
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Nation
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Tier
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Type

class CommonTanksRepositoryImpl(
    private val offlineDataSource: OfflineDataSource,
) : CommonTanksRepository {
    override fun getTiers(id: String): List<Tier> = offlineDataSource.getProperties(id, Tier.defaultValues)
    override fun setTiers(id: String, tiers: List<Tier>) = offlineDataSource.setProperties(id, tiers)

    override fun getNations(id: String): List<Nation> = offlineDataSource.getProperties(id, Nation.defaultValues)
    override fun setNations(id: String, nations: List<Nation>) = offlineDataSource.setProperties(id, nations)

    override fun getTypes(id: String): List<Type> = offlineDataSource.getProperties(id, Type.defaultValues)
    override fun setTypes(id: String, types: List<Type>) = offlineDataSource.setProperties(id, types)
}
