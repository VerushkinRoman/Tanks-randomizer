package com.posse.tanksrandomizer.feature_online_screen.data.models

import com.posse.tanksrandomizer.feature_online_screen.domain.models.Tank
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class DBTank: RealmObject {
    @PrimaryKey var id: Int = 0
    var name: String = ""
    var imageUrl: String? = null
    var tier: Int = 0
    var nationName: String = ""
    var isPremium: Boolean = false
    var typeName: String = ""
    var mastery: Int = 0
}

fun Tank.toDBTank(): DBTank {
    return DBTank().apply {
        id = this@toDBTank.id
        name = this@toDBTank.name
        imageUrl = this@toDBTank.imageUrl
        tier = this@toDBTank.tier
        nationName = this@toDBTank.nationName
        isPremium = this@toDBTank.isPremium
        typeName = this@toDBTank.typeName
        mastery = this@toDBTank.mastery
    }
}

fun DBTank.toTank(): Tank {
    return Tank(
        id = id,
        name = name,
        imageUrl = imageUrl,
        tier = tier,
        nationName = nationName,
        isPremium = isPremium,
        typeName = typeName,
        mastery = mastery,
    )
}
