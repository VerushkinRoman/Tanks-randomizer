package com.posse.tanksrandomizer.common.presentation.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import java.lang.reflect.Modifier
import kotlin.reflect.KClass

actual fun getPlatformFactory(): ViewModelProvider.Factory? {
    return object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(
            modelClass: KClass<T>,
            extras: CreationExtras
        ): T {
            val constructor =
                try {
                    modelClass.java.getDeclaredConstructor()
                } catch (e: NoSuchMethodException) {
                    throw RuntimeException("Cannot create an instance of $modelClass", e)
                }

            if (!Modifier.isPublic(constructor.modifiers)) {
                throw RuntimeException("Cannot create an instance of $modelClass")
            }

            return try {
                constructor.newInstance()
            } catch (e: InstantiationException) {
                throw RuntimeException("Cannot create an instance of $modelClass", e)
            } catch (e: IllegalAccessException) {
                throw RuntimeException("Cannot create an instance of $modelClass", e)
            }
        }
    }
}
