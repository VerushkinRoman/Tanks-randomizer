package com.posse.tanksrandomizer.common.domain.repository

import com.posse.tanksrandomizer.common.domain.models.FilterObjects.Experience
import com.posse.tanksrandomizer.common.domain.models.FilterObjects.Level
import com.posse.tanksrandomizer.common.domain.models.FilterObjects.Nation
import com.posse.tanksrandomizer.common.domain.models.FilterObjects.Pinned
import com.posse.tanksrandomizer.common.domain.models.FilterObjects.Status
import com.posse.tanksrandomizer.common.domain.models.FilterObjects.TankType
import com.posse.tanksrandomizer.common.domain.models.FilterObjects.Type

interface FilterRepository {
    fun getLevels(): List<Level>
    fun setLevels(levels: List<Level>)

    fun getExperiences(): List<Experience>
    fun setExperiences(experiences: List<Experience>)

    fun getNations(): List<Nation>
    fun setNations(nations: List<Nation>)

    fun getPinned(): List<Pinned>
    fun setPinned(pinned: List<Pinned>)

    fun getStatuses(): List<Status>
    fun setStatuses(statuses: List<Status>)

    fun getTankTypes(): List<TankType>
    fun setTankTypes(tankTypes: List<TankType>)

    fun getTypes(): List<Type>
    fun setTypes(types: List<Type>)
}