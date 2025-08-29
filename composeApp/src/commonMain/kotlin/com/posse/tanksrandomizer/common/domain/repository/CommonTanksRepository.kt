package com.posse.tanksrandomizer.common.domain.repository

import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Nation
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Tier
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Type

interface CommonTanksRepository {
    fun getLevels(): List<Tier>
    fun setLevels(tiers: List<Tier>)

    fun getNations(): List<Nation>
    fun setNations(nations: List<Nation>)

    fun getTypes(): List<Type>
    fun setTypes(types: List<Type>)
}
