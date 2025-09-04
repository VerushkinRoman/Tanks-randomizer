package com.posse.tanksrandomizer.feature_offline_screen.data.datasource

interface OfflineScreenDataSource {
    fun setQuantity(quantity: Int)
    fun getQuantity(): Int
}
