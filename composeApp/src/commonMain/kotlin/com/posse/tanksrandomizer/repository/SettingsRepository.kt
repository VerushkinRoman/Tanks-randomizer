package com.posse.tanksrandomizer.repository

import com.posse.tanksrandomizer.repository.model.FilterObjects.Experience
import com.posse.tanksrandomizer.repository.model.FilterObjects.Level
import com.posse.tanksrandomizer.repository.model.FilterObjects.Nation
import com.posse.tanksrandomizer.repository.model.FilterObjects.Pinned
import com.posse.tanksrandomizer.repository.model.FilterObjects.Status
import com.posse.tanksrandomizer.repository.model.FilterObjects.TankType
import com.posse.tanksrandomizer.repository.model.FilterObjects.Type

interface SettingsRepository {
    var levels: List<Level>
    var experiences: List<Experience>
    var nations: List<Nation>
    var pinned: List<Pinned>
    var statuses: List<Status>
    var tankTypes: List<TankType>
    var types: List<Type>
    var quantity: Int
}
