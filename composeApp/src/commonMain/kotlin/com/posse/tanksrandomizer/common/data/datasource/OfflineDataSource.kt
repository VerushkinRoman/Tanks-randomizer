package com.posse.tanksrandomizer.common.data.datasource

import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.ItemStatus
import com.posse.tanksrandomizer.common.domain.models.Token

interface OfflineDataSource {
    fun <T : ItemStatus<T>> setProperties(properties: List<T>)
    fun <T : ItemStatus<T>> getProperties(defaultItems: List<T>): List<T>

    fun setCurrentScreenRoute(screenRoute: String)
    fun getScreenRoute(): String?

    fun getToken(): Token?
    fun setToken(token: Token?)
}
