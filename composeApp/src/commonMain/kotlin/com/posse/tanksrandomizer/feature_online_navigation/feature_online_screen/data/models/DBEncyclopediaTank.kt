package com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.data.models

import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.EncyclopediaTank
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class DBEncyclopediaTank : RealmObject {
    @PrimaryKey
    var id: Int = 0
    var isPremium: Boolean = false
    var name: String = ""
    var nationName: String = ""
    var tier: Int = 0
    var typeName: String = ""
    var image: String? = null
}

fun EncyclopediaTank.toDBEncyclopediaTank(): DBEncyclopediaTank {
    return DBEncyclopediaTank().apply {
        id = this@toDBEncyclopediaTank.id
        isPremium = this@toDBEncyclopediaTank.isPremium
        name = this@toDBEncyclopediaTank.name
        nationName = this@toDBEncyclopediaTank.nationName
        tier = this@toDBEncyclopediaTank.tier
        typeName = this@toDBEncyclopediaTank.typeName
        image = this@toDBEncyclopediaTank.image
    }
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
