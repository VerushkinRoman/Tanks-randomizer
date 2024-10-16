package com.posse.tanksrandomizer.repository

import com.posse.tanksrandomizer.data_source.DataSource
import com.posse.tanksrandomizer.repository.model.Experience
import com.posse.tanksrandomizer.repository.model.Level
import com.posse.tanksrandomizer.repository.model.Nation
import com.posse.tanksrandomizer.repository.model.Pinned
import com.posse.tanksrandomizer.repository.model.Status
import com.posse.tanksrandomizer.repository.model.TankType
import com.posse.tanksrandomizer.repository.model.Type

class SettingsRepositoryImpl(
    private val dataSource: DataSource
) : SettingsRepository {
    override var levels: List<Level>
        get() = dataSource.getProperties(Level.allValues)
        set(value) = dataSource.setProperties(value)

    override var experiences: List<Experience>
        get() = dataSource.getProperties(Experience.allValues)
        set(value) = dataSource.setProperties(value)

    override var nations: List<Nation>
        get() = dataSource.getProperties(Nation.allValues)
        set(value) = dataSource.setProperties(value)

    override var pinned: Pinned
        get() = dataSource.getProperty(Pinned.Status())
        set(value) = dataSource.setProperty(value)

    override var statuses: List<Status>
        get() = dataSource.getProperties(Status.allValues)
        set(value) = dataSource.setProperties(value)

    override var tankTypes: List<TankType>
        get() = dataSource.getProperties(TankType.allValues)
        set(value) = dataSource.setProperties(value)

    override var types: List<Type>
        get() = dataSource.getProperties(Type.allValues)
        set(value) = dataSource.setProperties(value)

    override var quantity: Int
        get() = dataSource.getQuantity()
        set(value) = dataSource.setQuantity(value)
}