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
    override fun getQuantity(id: String): Int = offlineScreenDataSource.getQuantity()
    override fun setQuantity(id: String, quantity: Int) = offlineScreenDataSource.setQuantity(quantity)

    override fun getExperiences(id: String): List<Experience> = offlineDataSource.getProperties(id, Experience.defaultValues)
    override fun setExperiences(id: String, experiences: List<Experience>) = offlineDataSource.setProperties(id, experiences)

    override fun getTankTypes(id: String): List<TankType> = offlineDataSource.getProperties(id, TankType.defaultValues)
    override fun setTankTypes(id: String, tankTypes: List<TankType>) = offlineDataSource.setProperties(id, tankTypes)

    override fun getPinned(id: String): List<Pinned> = offlineDataSource.getProperties(id, Pinned.defaultValues)
    override fun setPinned(id: String, pinned: List<Pinned>) = offlineDataSource.setProperties(id, pinned)

    override fun getStatuses(id: String): List<Status> = offlineDataSource.getProperties(id, Status.defaultValues)
    override fun setStatuses(id: String, statuses: List<Status>) = offlineDataSource.setProperties(id, statuses)
}
