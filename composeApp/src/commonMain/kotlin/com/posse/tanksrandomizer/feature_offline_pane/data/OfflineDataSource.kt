package com.posse.tanksrandomizer.feature_offline_pane.data

interface OfflineDataSource {
    fun setQuantity(quantity: Int)
    fun getQuantity(): Int
}