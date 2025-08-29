package com.posse.tanksrandomizer.common.domain.repository

import com.posse.tanksrandomizer.common.data.OfflineCommonDataSource
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Nation
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Tier
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Type

class CommonTanksRepositoryImpl(
    private val offlineCommonDataSource: OfflineCommonDataSource,
) : CommonTanksRepository {
    override fun getLevels(): List<Tier> = offlineCommonDataSource.getProperties(Tier.defaultValues)
    override fun setLevels(tiers: List<Tier>) = offlineCommonDataSource.setProperties(tiers)

    override fun getNations(): List<Nation> = offlineCommonDataSource.getProperties(Nation.defaultValues)
    override fun setNations(nations: List<Nation>) = offlineCommonDataSource.setProperties(nations)

    override fun getTypes(): List<Type> = offlineCommonDataSource.getProperties(Type.defaultValues)
    override fun setTypes(types: List<Type>) = offlineCommonDataSource.setProperties(types)
}
