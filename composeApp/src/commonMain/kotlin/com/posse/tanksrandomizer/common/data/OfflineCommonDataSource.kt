package com.posse.tanksrandomizer.common.data

import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.ItemStatus
import com.posse.tanksrandomizer.common.domain.models.ScreenRoute
import com.posse.tanksrandomizer.common.domain.models.Token

interface OfflineCommonDataSource {
    fun <T : ItemStatus<T>> setProperties(properties: List<T>)
    fun <T : ItemStatus<T>> getProperties(defaultItems: List<T>): List<T>
    fun setCurrentScreenRoute(screenRoute: ScreenRoute)
    fun getScreenRoute(): ScreenRoute?
    fun getToken(): Token?
    fun setToken(token: Token?)
}
