package com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.presentation.use_cases

import com.posse.tanksrandomizer.common.domain.utils.Dispatchers
import com.posse.tanksrandomizer.feature_online_navigation.feature_online_screen.domain.models.Tank
import kotlinx.coroutines.withContext
import kotlin.random.Random

internal class GenerateTank(
    private val dispatchers: Dispatchers
) {
    suspend operator fun invoke(filteredTanks: List<Tank>, previousTank: Tank?): Tank? {
        return withContext(dispatchers.io) {
            when {
                filteredTanks.isEmpty() -> previousTank
                singleChoice(filteredTanks) -> generate(filteredTanks)
                else -> generateUniqueTank(filteredTanks, previousTank)
            }
        }
    }

    private fun generateUniqueTank(filteredTanks: List<Tank>, previousTank: Tank?): Tank {
        return generateSequence { generate(filteredTanks) }
            .first { isTankAllowed(generatedTank = it, previousTank = previousTank) }
    }

    private fun isTankAllowed(generatedTank: Tank, previousTank: Tank?): Boolean {
        val allowDuplicate = Random.nextFloat() < 0.3
        val tanksAreDifferent = generatedTank != previousTank
        return tanksAreDifferent || allowDuplicate
    }

    private fun singleChoice(tanks: List<Tank>): Boolean {
        return tanks.size == 1
    }

    private fun generate(tanks: List<Tank>): Tank {
        return tanks.random()
    }
}
