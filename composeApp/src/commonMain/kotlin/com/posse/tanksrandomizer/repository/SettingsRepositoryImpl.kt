package com.posse.tanksrandomizer.repository

import com.posse.tanksrandomizer.data_source.DataSource
import com.posse.tanksrandomizer.data_source.DataSourceMultiplatformSettings
import com.posse.tanksrandomizer.repository.model.FilterObjects.Experience
import com.posse.tanksrandomizer.repository.model.FilterObjects.Level
import com.posse.tanksrandomizer.repository.model.FilterObjects.Nation
import com.posse.tanksrandomizer.repository.model.FilterObjects.Pinned
import com.posse.tanksrandomizer.repository.model.FilterObjects.Status
import com.posse.tanksrandomizer.repository.model.FilterObjects.TankType
import com.posse.tanksrandomizer.repository.model.FilterObjects.Type
import com.posse.tanksrandomizer.utils.RotateDirection
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.SynchronizedObject
import kotlinx.coroutines.internal.synchronized
import kotlin.concurrent.Volatile

class SettingsRepositoryImpl private constructor(
    private val dataSource: DataSource = DataSourceMultiplatformSettings.getInstance()
) : SettingsRepository {
    override suspend fun getLevels(): List<Level> = dataSource.getProperties(Level.defaultValues)
    override suspend fun setLevels(levels: List<Level>) = dataSource.setProperties(levels)

    override suspend fun getExperiences(): List<Experience> = dataSource.getProperties(Experience.defaultValues)
    override suspend fun setExperiences(experiences: List<Experience>) = dataSource.setProperties(experiences)

    override suspend fun getNations(): List<Nation> = dataSource.getProperties(Nation.defaultValues)
    override suspend fun setNations(nations: List<Nation>) = dataSource.setProperties(nations)

    override suspend fun getPinned(): List<Pinned> = dataSource.getProperties(Pinned.defaultValues)
    override suspend fun setPinned(pinned: List<Pinned>) = dataSource.setProperties(pinned)

    override suspend fun getStatuses(): List<Status> = dataSource.getProperties(Status.defaultValues)
    override suspend fun setStatuses(statuses: List<Status>) = dataSource.setProperties(statuses)

    override suspend fun getTankTypes(): List<TankType> = dataSource.getProperties(TankType.defaultValues)
    override suspend fun setTankTypes(tankTypes: List<TankType>) = dataSource.setProperties(tankTypes)

    override suspend fun getTypes(): List<Type> = dataSource.getProperties(Type.defaultValues)
    override suspend fun setTypes(types: List<Type>) = dataSource.setProperties(types)

    override suspend fun getQuantity(): Int = dataSource.getQuantity()
    override suspend fun setQuantity(quantity: Int) = dataSource.setQuantity(quantity)

    override suspend fun getAutorotate(): Boolean = dataSource.getAutorotate()
    override suspend fun setAutorotate(autoRotate: Boolean) = dataSource.setAutorotate(autoRotate)

    override suspend fun getRotation(): RotateDirection {
        return RotateDirection.entries.find { it.value == dataSource.getRotation() } ?: RotateDirection.default
    }
    override suspend fun setRotation(rotateDirection: RotateDirection) = dataSource.setRotation(rotateDirection.value)

    @OptIn(InternalCoroutinesApi::class)
    companion object : SynchronizedObject() {
        @Volatile
        private var instance: SettingsRepository? = null
        fun getInstance() = instance ?: synchronized(this) {
            instance ?: SettingsRepositoryImpl().also { instance = it }
        }
    }
}