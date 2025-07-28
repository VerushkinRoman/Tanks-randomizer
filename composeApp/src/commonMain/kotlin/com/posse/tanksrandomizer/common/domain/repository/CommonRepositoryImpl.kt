package com.posse.tanksrandomizer.common.domain.repository

import com.posse.tanksrandomizer.common.data.DataSource
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Level
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Nation
import com.posse.tanksrandomizer.common.domain.models.CommonFilterObjects.Type

class CommonRepositoryImpl(
    private val dataSource: DataSource
) : CommonRepository {
    override fun getLevels(): List<Level> = dataSource.getProperties(Level.defaultValues)
    override fun setLevels(levels: List<Level>) = dataSource.setProperties(levels)

    override fun getNations(): List<Nation> = dataSource.getProperties(Nation.defaultValues)
    override fun setNations(nations: List<Nation>) = dataSource.setProperties(nations)

    override fun getTypes(): List<Type> = dataSource.getProperties(Type.defaultValues)
    override fun setTypes(types: List<Type>) = dataSource.setProperties(types)
}