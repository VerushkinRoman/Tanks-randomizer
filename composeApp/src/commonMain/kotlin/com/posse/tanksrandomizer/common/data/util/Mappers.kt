package com.posse.tanksrandomizer.common.data.util

import com.posse.tanksrandomizer.common.data.models.AccountTankData
import com.posse.tanksrandomizer.common.data.models.EncyclopediaTankData
import com.posse.tanksrandomizer.common.data.models.NetworkAuthData
import com.posse.tanksrandomizer.common.domain.models.AccountTank
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Nation
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Tier
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Type
import com.posse.tanksrandomizer.common.domain.models.EncyclopediaTank
import com.posse.tanksrandomizer.common.domain.models.Token
import com.posse.tanksrandomizer.feature_online_screen.domain.models.OnlineFilterObjects.Mastery

fun EncyclopediaTankData.toEncyclopediaTank(): EncyclopediaTank? {
    val encyclopediaNation = Nation.defaultValues.find { it.name == nation } ?: return null
    val encyclopediaTier = Tier.defaultValues.find { it.sort == tier } ?: return null
    val encyclopediaType = Type.defaultValues.find { it.name == type } ?: return null

    return EncyclopediaTank(
        id = tankId,
        isPremium = isPremium,
        name = name,
        nation = encyclopediaNation,
        tier = encyclopediaTier,
        type = encyclopediaType,
        image = images.preview,
    )
}

fun AccountTankData.toAccountTank(): AccountTank? {
    return AccountTank(
        id = tankId,
        mastery = Mastery.defaultValues.find { it.sort - 1 == markOfMastery }
            ?: return null
    )
}

fun NetworkAuthData.toToken(): Token {
    return Token(
        accessToken = accessToken,
        accountId = accountId,
        expiresAt = expiresAt,
    )
}
