package com.posse.tanksrandomizer.feature_offline_navigation.feature_offline_screen.data.datasource

interface OfflineScreenDataSource {
    fun setQuantity(id: String, quantity: Int)
    fun getQuantity(id: String): Int
}
