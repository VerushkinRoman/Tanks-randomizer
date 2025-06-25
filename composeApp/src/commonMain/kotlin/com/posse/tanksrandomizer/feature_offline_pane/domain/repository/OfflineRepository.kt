package com.posse.tanksrandomizer.feature_offline_pane.domain.repository

import com.posse.tanksrandomizer.feature_offline_pane.domain.models.OfflineFilterObjects.Experience
import com.posse.tanksrandomizer.feature_offline_pane.domain.models.OfflineFilterObjects.Pinned
import com.posse.tanksrandomizer.feature_offline_pane.domain.models.OfflineFilterObjects.Status

interface OfflineRepository {
    fun getQuantity(): Int
    fun setQuantity(quantity: Int)

    fun getExperiences(): List<Experience>
    fun setExperiences(experiences: List<Experience>)

    fun getPinned(): List<Pinned>
    fun setPinned(pinned: List<Pinned>)

    fun getStatuses(): List<Status>
    fun setStatuses(statuses: List<Status>)
}