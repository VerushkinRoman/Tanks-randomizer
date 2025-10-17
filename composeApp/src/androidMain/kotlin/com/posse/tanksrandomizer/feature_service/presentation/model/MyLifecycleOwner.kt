package com.posse.tanksrandomizer.feature_service.presentation.model

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.savedstate.SavedStateRegistry
import androidx.savedstate.SavedStateRegistryController
import androidx.savedstate.SavedStateRegistryOwner

internal class MyLifecycleOwner(
    private val lifecycleOwner: LifecycleOwner
) : SavedStateRegistryOwner {
    private var savedStateRegistryController: SavedStateRegistryController =
        SavedStateRegistryController.create(this)

    override val lifecycle: Lifecycle
        get() = lifecycleOwner.lifecycle

    override val savedStateRegistry: SavedStateRegistry
        get() = savedStateRegistryController.savedStateRegistry

    fun performRestore(savedState: Bundle?) {
        savedStateRegistryController.performRestore(savedState)
    }
}
