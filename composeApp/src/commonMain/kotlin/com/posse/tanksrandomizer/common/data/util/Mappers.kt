package com.posse.tanksrandomizer.common.data.util

import com.posse.tanksrandomizer.common.data.models.NetworkAccountTank
import com.posse.tanksrandomizer.common.data.models.EncyclopediaTankData
import com.posse.tanksrandomizer.common.data.models.NetworkAuthData
import com.posse.tanksrandomizer.common.domain.models.AccountTank
import com.posse.tanksrandomizer.common.domain.models.EncyclopediaTank
import com.posse.tanksrandomizer.common.domain.models.Token

fun EncyclopediaTankData.toEncyclopediaTank(): EncyclopediaTank {
    return EncyclopediaTank(
        id = tankId,
        isPremium = isPremium,
        name = name,
        nationName = nation,
        tier = tier,
        typeName = type,
        image = images.preview,
    )
}

fun NetworkAccountTank.toAccountTank(): AccountTank {
    return AccountTank(
        id = tankId,
        mastery = markOfMastery + 1
    )
}

fun NetworkAuthData.toToken(): Token {
    return Token(
        accessToken = accessToken,
        accountId = accountId,
        expiresAt = expiresAt,
    )
}
