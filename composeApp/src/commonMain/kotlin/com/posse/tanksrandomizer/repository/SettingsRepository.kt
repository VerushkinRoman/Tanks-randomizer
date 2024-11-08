package com.posse.tanksrandomizer.repository

import com.posse.tanksrandomizer.repository.model.FilterObjects.Experience
import com.posse.tanksrandomizer.repository.model.FilterObjects.Level
import com.posse.tanksrandomizer.repository.model.FilterObjects.Nation
import com.posse.tanksrandomizer.repository.model.FilterObjects.Pinned
import com.posse.tanksrandomizer.repository.model.FilterObjects.Status
import com.posse.tanksrandomizer.repository.model.FilterObjects.TankType
import com.posse.tanksrandomizer.repository.model.FilterObjects.Type
import com.posse.tanksrandomizer.utils.RotateDirection

interface SettingsRepository {
    suspend fun getLevels(): List<Level>
    suspend fun setLevels(levels: List<Level>)

    suspend fun getExperiences(): List<Experience>
    suspend fun setExperiences(experiences: List<Experience>)

    suspend fun getNations(): List<Nation>
    suspend fun setNations(nations: List<Nation>)

    suspend fun getPinned(): List<Pinned>
    suspend fun setPinned(pinned: List<Pinned>)

    suspend fun getStatuses(): List<Status>
    suspend fun setStatuses(statuses: List<Status>)

    suspend fun getTankTypes(): List<TankType>
    suspend fun setTankTypes(tankTypes: List<TankType>)

    suspend fun getTypes(): List<Type>
    suspend fun setTypes(types: List<Type>)

    suspend fun getQuantity(): Int
    suspend fun setQuantity(quantity: Int)

    suspend fun getAutorotate(): Boolean
    suspend fun setAutorotate(autoRotate: Boolean)

    suspend fun getRotation(): RotateDirection
    suspend fun setRotation(rotateDirection: RotateDirection)
}