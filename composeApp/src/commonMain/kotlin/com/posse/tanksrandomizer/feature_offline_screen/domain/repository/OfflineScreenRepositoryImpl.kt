package com.posse.tanksrandomizer.feature_offline_screen.domain.repository

import com.posse.tanksrandomizer.common.data.datasource.OfflineDataSource
import com.posse.tanksrandomizer.feature_offline_screen.data.datasource.OfflineScreenDataSource
import com.posse.tanksrandomizer.feature_offline_screen.domain.models.OfflineFilterObjects.Experience
import com.posse.tanksrandomizer.feature_offline_screen.domain.models.OfflineFilterObjects.Pinned
import com.posse.tanksrandomizer.feature_offline_screen.domain.models.OfflineFilterObjects.Status
import com.posse.tanksrandomizer.feature_offline_screen.domain.models.OfflineFilterObjects.TankType

class OfflineScreenRepositoryImpl(
    private val offlineDataSource: OfflineDataSource,
    private val offlineScreenDataSource: OfflineScreenDataSource,
) : OfflineScreenRepository {
    override fun getQuantity(): Int = offlineScreenDataSource.getQuantity()
    override fun setQuantity(quantity: Int) = offlineScreenDataSource.setQuantity(quantity)

    override fun getExperiences(): List<Experience> = offlineDataSource.getProperties(defaultItems = Experience.defaultValues)
    override fun setExperiences(experiences: List<Experience>) = offlineDataSource.setProperties(experiences)

    override fun getTankTypes(): List<TankType> = offlineDataSource.getProperties(defaultItems = TankType.defaultValues)
    override fun setTankTypes(tankTypes: List<TankType>) = offlineDataSource.setProperties(tankTypes)

    override fun getPinned(): List<Pinned> = offlineDataSource.getProperties(defaultItems = Pinned.defaultValues)
    override fun setPinned(pinned: List<Pinned>) = offlineDataSource.setProperties(pinned)

    override fun getStatuses(): List<Status> = offlineDataSource.getProperties(defaultItems = Status.defaultValues)
    override fun setStatuses(statuses: List<Status>) = offlineDataSource.setProperties(statuses)
}
