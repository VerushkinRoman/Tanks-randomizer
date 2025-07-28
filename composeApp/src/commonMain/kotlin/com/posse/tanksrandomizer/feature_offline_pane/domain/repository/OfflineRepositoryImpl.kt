package com.posse.tanksrandomizer.feature_offline_pane.domain.repository

import com.posse.tanksrandomizer.common.data.DataSource
import com.posse.tanksrandomizer.feature_offline_pane.data.OfflineDataSource
import com.posse.tanksrandomizer.feature_offline_pane.domain.models.OfflineFilterObjects.Experience
import com.posse.tanksrandomizer.feature_offline_pane.domain.models.OfflineFilterObjects.Pinned
import com.posse.tanksrandomizer.feature_offline_pane.domain.models.OfflineFilterObjects.Status
import com.posse.tanksrandomizer.feature_offline_pane.domain.models.OfflineFilterObjects.TankType

class OfflineRepositoryImpl(
    private val dataSource: DataSource,
    private val offlineDataSource: OfflineDataSource
) : OfflineRepository {
    override fun getQuantity(): Int = offlineDataSource.getQuantity()
    override fun setQuantity(quantity: Int) = offlineDataSource.setQuantity(quantity)

    override fun getExperiences(): List<Experience> = dataSource.getProperties(Experience.defaultValues)
    override fun setExperiences(experiences: List<Experience>) = dataSource.setProperties(experiences)

    override fun getTankTypes(): List<TankType> = dataSource.getProperties(TankType.defaultValues)
    override fun setTankTypes(tankTypes: List<TankType>) = dataSource.setProperties(tankTypes)

    override fun getPinned(): List<Pinned> = dataSource.getProperties(Pinned.defaultValues)
    override fun setPinned(pinned: List<Pinned>) = dataSource.setProperties(pinned)

    override fun getStatuses(): List<Status> = dataSource.getProperties(Status.defaultValues)
    override fun setStatuses(statuses: List<Status>) = dataSource.setProperties(statuses)
}