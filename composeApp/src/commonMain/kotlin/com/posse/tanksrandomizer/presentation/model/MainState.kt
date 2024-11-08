package com.posse.tanksrandomizer.presentation.model

data class MainState(
    val filters: Filters = Filters(),
    val numbers: Numbers = Numbers(),
    val rotation: Rotation = Rotation(),
)