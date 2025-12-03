package com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.data.models

import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.MasteryTank
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class DBMasteryTank : RealmObject {
    @Suppress("PropertyName", "unused")
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var accountId: Int = 0
    var tankId: Int = 0
    var mastery: Int = 0
}

fun MasteryTank.toDBMasteryTank(): DBMasteryTank {
    return DBMasteryTank().apply {
        accountId = this@toDBMasteryTank.accountId
        tankId = this@toDBMasteryTank.tankId
        mastery = this@toDBMasteryTank.mastery
    }
}

fun DBMasteryTank.toMasteryTank(): MasteryTank {
    return MasteryTank(
        accountId = accountId,
        tankId = tankId,
        mastery = mastery,
    )
}
