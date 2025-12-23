package com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.data.database.models

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.EncyclopediaTank

@Entity(
    indices = [
        Index(value = ["nationName"]),
        Index(value = ["tier"]),
        Index(value = ["typeName"]),
        Index(value = ["name"])
    ]
)
class DBEncyclopediaTank(
    @PrimaryKey val id: Int,
    val isPremium: Boolean,
    val name: String,
    val nationName: String,
    val tier: Int,
    val typeName: String,
    val image: String?,
)

fun EncyclopediaTank.toDBEncyclopediaTank(): DBEncyclopediaTank {
    return DBEncyclopediaTank(
        id = id,
        isPremium = isPremium,
        name = name,
        nationName = nationName,
        tier = tier,
        typeName = typeName,
        image = image,
    )
}

fun DBEncyclopediaTank.toEncyclopediaTank(): EncyclopediaTank {
    return EncyclopediaTank(
        id = id,
        isPremium = isPremium,
        name = name,
        nationName = nationName,
        tier = tier,
        typeName = typeName,
        image = image,
    )
}
