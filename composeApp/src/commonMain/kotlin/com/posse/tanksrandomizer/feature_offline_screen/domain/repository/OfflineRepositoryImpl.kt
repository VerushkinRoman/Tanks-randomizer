package com.posse.tanksrandomizer.feature_offline_screen.domain.repository

import com.posse.tanksrandomizer.common.data.OfflineCommonDataSource
import com.posse.tanksrandomizer.feature_offline_screen.data.OfflineDataSource
import com.posse.tanksrandomizer.feature_offline_screen.domain.models.OfflineFilterObjects.Experience
import com.posse.tanksrandomizer.feature_offline_screen.domain.models.OfflineFilterObjects.Pinned
import com.posse.tanksrandomizer.feature_offline_screen.domain.models.OfflineFilterObjects.Status
import com.posse.tanksrandomizer.feature_offline_screen.domain.models.OfflineFilterObjects.TankType

class OfflineRepositoryImpl(
    private val offlineCommonDataSource: OfflineCommonDataSource,
    private val offlineDataSource: OfflineDataSource,
) : OfflineRepository {
    override fun getQuantity(): Int = offlineDataSource.getQuantity()
    override fun setQuantity(quantity: Int) = offlineDataSource.setQuantity(quantity)

    override fun getExperiences(): List<Experience> = offlineCommonDataSource.getProperties(Experience.defaultValues)
    override fun setExperiences(experiences: List<Experience>) = offlineCommonDataSource.setProperties(experiences)

    override fun getTankTypes(): List<TankType> = offlineCommonDataSource.getProperties(TankType.defaultValues)
    override fun setTankTypes(tankTypes: List<TankType>) = offlineCommonDataSource.setProperties(tankTypes)

    override fun getPinned(): List<Pinned> = offlineCommonDataSource.getProperties(Pinned.defaultValues)
    override fun setPinned(pinned: List<Pinned>) = offlineCommonDataSource.setProperties(pinned)

    override fun getStatuses(): List<Status> = offlineCommonDataSource.getProperties(Status.defaultValues)
    override fun setStatuses(statuses: List<Status>) = offlineCommonDataSource.setProperties(statuses)
}
