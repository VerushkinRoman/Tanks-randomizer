package com.posse.tanksrandomizer.feature_offline_screen.domain.repository

import com.posse.tanksrandomizer.feature_offline_screen.domain.models.OfflineFilterObjects.Experience
import com.posse.tanksrandomizer.feature_offline_screen.domain.models.OfflineFilterObjects.Pinned
import com.posse.tanksrandomizer.feature_offline_screen.domain.models.OfflineFilterObjects.Status
import com.posse.tanksrandomizer.feature_offline_screen.domain.models.OfflineFilterObjects.TankType

interface OfflineScreenRepository {
    fun getQuantity(id: String): Int
    fun setQuantity(id: String, quantity: Int)

    fun getExperiences(id: String): List<Experience>
    fun setExperiences(id: String, experiences: List<Experience>)

    fun getTankTypes(id: String): List<TankType>
    fun setTankTypes(id: String, tankTypes: List<TankType>)

    fun getPinned(id: String): List<Pinned>
    fun setPinned(id: String, pinned: List<Pinned>)

    fun getStatuses(id: String): List<Status>
    fun setStatuses(id: String, statuses: List<Status>)
}
