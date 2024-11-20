package com.posse.tanksrandomizer.feature_main_screen.domain.repository

import com.posse.tanksrandomizer.common.domain.model.FilterObjects.Experience
import com.posse.tanksrandomizer.common.domain.model.FilterObjects.Level
import com.posse.tanksrandomizer.common.domain.model.FilterObjects.Nation
import com.posse.tanksrandomizer.common.domain.model.FilterObjects.Pinned
import com.posse.tanksrandomizer.common.domain.model.FilterObjects.Status
import com.posse.tanksrandomizer.common.domain.model.FilterObjects.TankType
import com.posse.tanksrandomizer.common.domain.model.FilterObjects.Type

interface MainRepository {
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

    fun getQuantity(): Int
    fun setQuantity(quantity: Int)
}