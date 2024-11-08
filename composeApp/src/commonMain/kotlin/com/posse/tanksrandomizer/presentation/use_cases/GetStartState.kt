package com.posse.tanksrandomizer.presentation.use_cases

import com.posse.tanksrandomizer.presentation.model.Filters
import com.posse.tanksrandomizer.presentation.model.MainState
import com.posse.tanksrandomizer.presentation.model.Numbers
import com.posse.tanksrandomizer.presentation.model.Rotation
import com.posse.tanksrandomizer.utils.DataPreloader

class GetStartState {
    operator fun invoke(): MainState {
        val dataPreloader = DataPreloader.getInstance()

        val mainState = dataPreloader.preloadedData?.let { data ->
            MainState(
                filters = Filters(
                    levels = data.levels,
                    experiences = data.experiences,
                    nations = data.nations,
                    pinned = data.pinned,
                    statuses = data.statuses,
                    tankTypes = data.tankTypes,
                    types = data.types,
                ),
                numbers = Numbers(
                    quantity = data.quantity,
                ),
                rotation = Rotation(
                    autoRotateEnabled = data.autorotate,
                    rotateDirection = data.rotateDirection,
                )
            )
        } ?: MainState()

        dataPreloader.clearData()

        return mainState
    }
}