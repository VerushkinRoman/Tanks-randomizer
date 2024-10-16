package com.posse.tanksrandomizer.repository

import com.posse.tanksrandomizer.repository.model.Experience
import com.posse.tanksrandomizer.repository.model.Level
import com.posse.tanksrandomizer.repository.model.Nation
import com.posse.tanksrandomizer.repository.model.Pinned
import com.posse.tanksrandomizer.repository.model.Status
import com.posse.tanksrandomizer.repository.model.TankType
import com.posse.tanksrandomizer.repository.model.Type

interface SettingsRepository {
    var levels: List<Level>
    var experiences: List<Experience>
    var nations: List<Nation>
    var pinned: Pinned
    var statuses: List<Status>
    var tankTypes: List<TankType>
    var types: List<Type>
    var quantity: Int
}
