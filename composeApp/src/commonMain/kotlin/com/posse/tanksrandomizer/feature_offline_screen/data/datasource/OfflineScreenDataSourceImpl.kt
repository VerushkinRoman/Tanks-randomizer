package com.posse.tanksrandomizer.feature_offline_screen.data.datasource

import com.russhwolf.settings.Settings

class OfflineScreenDataSourceImpl(
    private val settings: Settings,
) : OfflineScreenDataSource {
    override fun getQuantity(): Int = settings.getInt("quantity", 1)
    override fun setQuantity(quantity: Int) = settings.putInt("quantity", quantity)
}
