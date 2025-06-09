package com.posse.tanksrandomizer.feature_offline_pane.domain.repository

interface OfflineRepository {
    fun getQuantity(): Int
    fun setQuantity(quantity: Int)
}