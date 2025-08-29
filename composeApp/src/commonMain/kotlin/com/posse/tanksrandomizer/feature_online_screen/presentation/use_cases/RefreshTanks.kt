package com.posse.tanksrandomizer.feature_online_screen.presentation.use_cases

import com.posse.tanksrandomizer.common.domain.utils.Dispatchers
import com.posse.tanksrandomizer.common.domain.utils.Error
import com.posse.tanksrandomizer.common.domain.utils.Result
import com.posse.tanksrandomizer.common.domain.utils.onError
import com.posse.tanksrandomizer.common.domain.models.AccountTank
import com.posse.tanksrandomizer.common.domain.models.EncyclopediaTank
import com.posse.tanksrandomizer.feature_online_screen.domain.models.Tank
import com.posse.tanksrandomizer.feature_online_screen.domain.repository.OnlineRepository
import kotlinx.coroutines.withContext

class RefreshTanks(
    private val onlineRepository: OnlineRepository,
    private val dispatchers: Dispatchers,
) {
    suspend operator fun invoke(tanks: List<Tank>): Result<List<Tank>, Error> {
        return withContext(dispatchers.io) {
            val accountTanks: List<AccountTank> = onlineRepository.getAccountTanks()
                .onError { error -> return@withContext Result.Error(error) }
                .let { (it as Result.Success).data }

            val tankIds = tanks.map { it.id }.toSet()
            val accountTanksToAdd = accountTanks.filter { it.id in tankIds }
            val diffIds = accountTanksToAdd.map { it.id }

            val encyclopediaTanks: List<EncyclopediaTank> =
                onlineRepository.getEncyclopediaTanks(diffIds)
                    .onError { error -> return@withContext Result.Error(error) }
                    .let { (it as Result.Success).data }

            val accountTanksIds = accountTanks.map { it.id }.toSet()
            val tanksToRemove = tanks.filter { it.id !in accountTanksIds }

            val tanksToAdd = buildList {
                encyclopediaTanks.forEach { encyclopediaTank ->
                    accountTanksToAdd.find { it.id == encyclopediaTank.id }?.let { accountTank ->
                        add(encyclopediaTank.toTank(isMaster = accountTank.isMaster))
                    }
                }
            }

            val newTanks: List<Tank> = tanks - tanksToRemove + tanksToAdd

            Result.Success(newTanks)
        }
    }

    private fun EncyclopediaTank.toTank(isMaster: Boolean): Tank {
        return Tank(
            id = id,
            name = name,
            imageUrl = image,
            tier = tier,
            nation = nation,
            isPremium = isPremium,
            type = type,
            isMaster = isMaster
        )
    }
}