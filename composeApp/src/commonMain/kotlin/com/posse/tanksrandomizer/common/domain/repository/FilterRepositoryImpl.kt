package com.posse.tanksrandomizer.common.domain.repository

import com.posse.tanksrandomizer.common.data.DataSource
import com.posse.tanksrandomizer.common.domain.models.FilterObjects.Experience
import com.posse.tanksrandomizer.common.domain.models.FilterObjects.Level
import com.posse.tanksrandomizer.common.domain.models.FilterObjects.Nation
import com.posse.tanksrandomizer.common.domain.models.FilterObjects.Pinned
import com.posse.tanksrandomizer.common.domain.models.FilterObjects.Status
import com.posse.tanksrandomizer.common.domain.models.FilterObjects.TankType
import com.posse.tanksrandomizer.common.domain.models.FilterObjects.Type

class FilterRepositoryImpl(
    private val dataSource: DataSource
): FilterRepository {
    override fun getLevels(): List<Level> = dataSource.getProperties(Level.defaultValues)
    override fun setLevels(levels: List<Level>) = dataSource.setProperties(levels)

    override fun getExperiences(): List<Experience> = dataSource.getProperties(Experience.defaultValues)
    override fun setExperiences(experiences: List<Experience>) = dataSource.setProperties(experiences)

    override fun getNations(): List<Nation> = dataSource.getProperties(Nation.defaultValues)
    override fun setNations(nations: List<Nation>) = dataSource.setProperties(nations)

    override fun getPinned(): List<Pinned> = dataSource.getProperties(Pinned.defaultValues)
    override fun setPinned(pinned: List<Pinned>) = dataSource.setProperties(pinned)

    override fun getStatuses(): List<Status> = dataSource.getProperties(Status.defaultValues)
    override fun setStatuses(statuses: List<Status>) = dataSource.setProperties(statuses)

    override fun getTankTypes(): List<TankType> = dataSource.getProperties(TankType.defaultValues)
    override fun setTankTypes(tankTypes: List<TankType>) = dataSource.setProperties(tankTypes)

    override fun getTypes(): List<Type> = dataSource.getProperties(Type.defaultValues)
    override fun setTypes(types: List<Type>) = dataSource.setProperties(types)
}