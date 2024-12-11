package com.posse.tanksrandomizer.feature_service.presentation.model

import android.view.View

class TanksLayoutChangeListener(
    private val onLayoutChange: ()->Unit
): View.OnLayoutChangeListener {
    override fun onLayoutChange(
        v: View?,
        left: Int,
        top: Int,
        right: Int,
        bottom: Int,
        oldLeft: Int,
        oldTop: Int,
        oldRight: Int,
        oldBottom: Int
    ) = onLayoutChange()
}