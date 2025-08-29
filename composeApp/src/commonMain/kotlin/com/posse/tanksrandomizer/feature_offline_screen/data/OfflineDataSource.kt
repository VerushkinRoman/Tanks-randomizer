package com.posse.tanksrandomizer.feature_offline_screen.data

interface OfflineDataSource {
    fun setQuantity(quantity: Int)
    fun getQuantity(): Int
}
