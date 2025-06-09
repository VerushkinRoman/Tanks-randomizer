package com.posse.tanksrandomizer.feature_offline_pane.domain.repository

import com.posse.tanksrandomizer.common.data.DataSource

class OfflineRepositoryImpl(
    private val dataSource: DataSource
) : OfflineRepository {
    override fun getQuantity(): Int = dataSource.getQuantity()
    override fun setQuantity(quantity: Int) = dataSource.setQuantity(quantity)
}