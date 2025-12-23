package com.posse.tanksrandomizer.feature_offline_navigation.feature_offline_screen.data.datasource

import com.russhwolf.settings.Settings

class OfflineScreenDataSourceImpl(
    private val settings: Settings,
) : OfflineScreenDataSource {
    override fun getQuantity(id: String): Int = settings.getInt("quantity_$id", 1)
    override fun setQuantity(id: String, quantity: Int) = settings.putInt("quantity_$id", quantity)
}
