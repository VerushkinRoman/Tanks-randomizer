package com.posse.tanksrandomizer.feature_offline_screen.domain.repository

import com.posse.tanksrandomizer.feature_offline_screen.domain.models.OfflineFilterObjects.Experience
import com.posse.tanksrandomizer.feature_offline_screen.domain.models.OfflineFilterObjects.Pinned
import com.posse.tanksrandomizer.feature_offline_screen.domain.models.OfflineFilterObjects.Status
import com.posse.tanksrandomizer.feature_offline_screen.domain.models.OfflineFilterObjects.TankType

interface OfflineRepository {
    fun getQuantity(): Int
    fun setQuantity(quantity: Int)

    fun getExperiences(): List<Experience>
    fun setExperiences(experiences: List<Experience>)

    fun getTankTypes(): List<TankType>
    fun setTankTypes(tankTypes: List<TankType>)

    fun getPinned(): List<Pinned>
    fun setPinned(pinned: List<Pinned>)

    fun getStatuses(): List<Status>
    fun setStatuses(statuses: List<Status>)
}