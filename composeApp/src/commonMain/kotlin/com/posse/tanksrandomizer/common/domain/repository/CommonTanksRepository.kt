package com.posse.tanksrandomizer.common.domain.repository

import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Nation
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Tier
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Type

interface CommonTanksRepository {
    fun getTiers(id: String): List<Tier>
    fun setTiers(id: String, tiers: List<Tier>)

    fun getNations(id: String): List<Nation>
    fun setNations(id: String, nations: List<Nation>)

    fun getTypes(id: String): List<Type>
    fun setTypes(id: String, types: List<Type>)
}
