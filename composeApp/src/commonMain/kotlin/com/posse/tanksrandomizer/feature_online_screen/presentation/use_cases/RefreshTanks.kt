package com.posse.tanksrandomizer.feature_online_screen.presentation.use_cases

import com.posse.tanksrandomizer.common.domain.models.AccountTank
import com.posse.tanksrandomizer.common.domain.models.EncyclopediaTank
import com.posse.tanksrandomizer.common.domain.models.toTank
import com.posse.tanksrandomizer.common.domain.utils.Dispatchers
import com.posse.tanksrandomizer.common.domain.utils.Error
import com.posse.tanksrandomizer.common.domain.utils.Result
import com.posse.tanksrandomizer.common.domain.utils.onError
import com.posse.tanksrandomizer.feature_online_screen.domain.models.Tank
import com.posse.tanksrandomizer.feature_online_screen.domain.repository.OnlineScreenRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RefreshTanks(
    private val onlineScreenRepository: OnlineScreenRepository,
    private val dispatchers: Dispatchers,
) {
    suspend operator fun invoke(tanks: List<Tank>): Result<List<Tank>, Error> {
        return withContext(dispatchers.io) {
            val accountTanks: Set<AccountTank> = onlineScreenRepository.getAccountTanks()
                .onError { error -> return@withContext Result.Error(error) }
                .let { (it as Result.Success).data }
                .toSet()
                .also { println(it.size) }

            val masteryUpdatedTanks = tanks.mapNotNull { tank ->
                accountTanks
                    .find { it.id == tank.id }
                    ?.let { tank.copy(mastery = it.mastery) }
            }.toSet()

            val tankIds = masteryUpdatedTanks.map { it.id }.toSet()
            val accountTanksToAdd = accountTanks.filter { it.id !in tankIds }.toSet()
            val diffIds = accountTanksToAdd.map { it.id }.toSet()

            val encyclopediaTanks: Set<EncyclopediaTank> =
                onlineScreenRepository.getEncyclopediaTanks(diffIds.toList())
                    .onError { error -> return@withContext Result.Error(error) }
                    .let { (it as Result.Success).data }
                    .toSet()

            val accountTanksIds = accountTanks.map { it.id }.toSet()
            val tanksToRemove = masteryUpdatedTanks.filter { it.id !in accountTanksIds }.toSet()

            val tanksToAdd = buildSet {
                encyclopediaTanks.forEach { encyclopediaTank ->
                    accountTanksToAdd.find { it.id == encyclopediaTank.id }?.let { accountTank ->
                        add(encyclopediaTank.toTank(mastery = accountTank.mastery))
                    }
                }
            }

            val newTanks: List<Tank> = (masteryUpdatedTanks - tanksToRemove + tanksToAdd).toList()

            launch {
                onlineScreenRepository.setTanksInGarage(newTanks)
            }

            Result.Success(newTanks)
        }
    }
}
