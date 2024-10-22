package com.posse.tanksrandomizer.repository

import com.posse.tanksrandomizer.data_source.DataSource
import com.posse.tanksrandomizer.repository.model.FilterObjects.Experience
import com.posse.tanksrandomizer.repository.model.FilterObjects.Level
import com.posse.tanksrandomizer.repository.model.FilterObjects.Nation
import com.posse.tanksrandomizer.repository.model.FilterObjects.Pinned
import com.posse.tanksrandomizer.repository.model.FilterObjects.Status
import com.posse.tanksrandomizer.repository.model.FilterObjects.TankType
import com.posse.tanksrandomizer.repository.model.FilterObjects.Type

class SettingsRepositoryImpl(
    private val dataSource: DataSource
) : SettingsRepository {
    override var levels: List<Level>
        get() = dataSource.getProperties(Level.defaultValues)
        set(value) = dataSource.setProperties(value)

    override var experiences: List<Experience>
        get() = dataSource.getProperties(Experience.defaultValues)
        set(value) = dataSource.setProperties(value)

    override var nations: List<Nation>
        get() = dataSource.getProperties(Nation.defaultValues)
        set(value) = dataSource.setProperties(value)

    override var pinned: List<Pinned>
        get() = dataSource.getProperties(Pinned.defaultValues)
        set(value) = dataSource.setProperties(value)

    override var statuses: List<Status>
        get() = dataSource.getProperties(Status.defaultValues)
        set(value) = dataSource.setProperties(value)

    override var tankTypes: List<TankType>
        get() = dataSource.getProperties(TankType.defaultValues)
        set(value) = dataSource.setProperties(value)

    override var types: List<Type>
        get() = dataSource.getProperties(Type.defaultValues)
        set(value) = dataSource.setProperties(value)

    override var quantity: Int
        get() = dataSource.getQuantity()
        set(value) = dataSource.setQuantity(value)
}