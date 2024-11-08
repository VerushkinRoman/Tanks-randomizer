package com.posse.tanksrandomizer.utils

import com.posse.tanksrandomizer.repository.SettingsRepository
import com.posse.tanksrandomizer.repository.SettingsRepositoryImpl
import com.posse.tanksrandomizer.repository.model.FilterObjects.Experience
import com.posse.tanksrandomizer.repository.model.FilterObjects.Level
import com.posse.tanksrandomizer.repository.model.FilterObjects.Nation
import com.posse.tanksrandomizer.repository.model.FilterObjects.Pinned
import com.posse.tanksrandomizer.repository.model.FilterObjects.Status
import com.posse.tanksrandomizer.repository.model.FilterObjects.TankType
import com.posse.tanksrandomizer.repository.model.FilterObjects.Type
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.SynchronizedObject
import kotlinx.coroutines.internal.synchronized
import kotlin.concurrent.Volatile

class BoxedInt(val int: Int)

@OptIn(InternalCoroutinesApi::class)
class DataPreloader private constructor() {
    private val repository: SettingsRepository = SettingsRepositoryImpl.getInstance()
    var preloadedData: PreloadedData? = null
        private set

    suspend fun preloadData(onFinished: () -> Unit) {
        instance?.let {
            it.preloadedData = PreloadedData(
                levels = it.repository.getLevels(),
                experiences = it.repository.getExperiences(),
                nations = it.repository.getNations(),
                pinned = it.repository.getPinned(),
                statuses = it.repository.getStatuses(),
                tankTypes = it.repository.getTankTypes(),
                types = it.repository.getTypes(),
                quantity = it.repository.getQuantity(),
                autorotate = it.repository.getAutorotate(),
                rotateDirection = it.repository.getRotation(),
            )

            onFinished()
        }
    }

    fun clearData() {
        preloadedData = null
        instance = null
    }

    companion object : SynchronizedObject() {

        @Volatile
        private var instance: DataPreloader? = null

        fun getInstance() = instance ?: synchronized(this) {
            instance ?: DataPreloader().also { instance = it }
        }
    }

    data class PreloadedData(
        val levels: List<Level>,
        val experiences: List<Experience>,
        val nations: List<Nation>,
        val pinned: List<Pinned>,
        val statuses: List<Status>,
        val tankTypes: List<TankType>,
        val types: List<Type>,
        val quantity: Int,
        val autorotate: Boolean,
        val rotateDirection: RotateDirection,
    )
}