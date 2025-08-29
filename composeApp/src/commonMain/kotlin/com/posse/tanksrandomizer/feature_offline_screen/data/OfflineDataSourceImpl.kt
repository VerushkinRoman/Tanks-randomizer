package com.posse.tanksrandomizer.feature_offline_screen.data

import com.russhwolf.settings.Settings

class OfflineDataSourceImpl(
    private val settings: Settings,
) : OfflineDataSource {
    override fun getQuantity(): Int = settings.getInt("quantity", 1)
    override fun setQuantity(quantity: Int) = settings.putInt("quantity", quantity)
}