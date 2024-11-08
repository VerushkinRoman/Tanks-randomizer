package com.posse.tanksrandomizer.utils

enum class RotateDirection(val value: String) {
    Portrait(value = "Portrait"),
    Landscape(value = "Landscape");

    companion object {
        val default: RotateDirection = Portrait
    }
}

