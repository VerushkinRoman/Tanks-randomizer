package com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.data.database.models

import androidx.room.Entity
import com.posse.tanksrandomizer.feature_online_navigation.common.domain.models.MasteryTank

@Entity(primaryKeys = ["accountId", "tankId"])
data class DBMasteryTank(
    val accountId: Int,
    val tankId: Int,
    val mastery: Int,
)

fun MasteryTank.toDBMasteryTank(): DBMasteryTank {
    return DBMasteryTank(
        accountId = accountId,
        tankId = tankId,
        mastery = mastery,
    )
}

fun DBMasteryTank.toMasteryTank(): MasteryTank {
    return MasteryTank(
        accountId = accountId,
        tankId = tankId,
        mastery = mastery,
    )
}
