package com.posse.tanksrandomizer.common.domain.repository

import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Level
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Nation
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Type

interface CommonRepository {
    fun getLevels(): List<Level>
    fun setLevels(levels: List<Level>)

    fun getNations(): List<Nation>
    fun setNations(nations: List<Nation>)

    fun getTypes(): List<Type>
    fun setTypes(types: List<Type>)
}