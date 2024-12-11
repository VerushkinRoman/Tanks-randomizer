package com.posse.tanksrandomizer.feature_service.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.graphics.Point
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowInsets
import android.view.WindowManager
import androidx.core.content.getSystemService

internal object WindowUtils {
    internal val Context.appWidth: Int
        get() {
            val windowManager = getSystemService<WindowManager>()

            return when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
                    val displayCutout = windowManager
                        ?.currentWindowMetrics
                        ?.windowInsets
                        ?.getInsets(WindowInsets.Type.displayCutout())
                        ?.let { it.left + it.right }
                        ?: 0

                    val systemBars = windowManager
                        ?.currentWindowMetrics
                        ?.windowInsets
                        ?.getInsets(WindowInsets.Type.systemBars())
                        ?.let { it.right + it.left }
                        ?: 0

                    screenWidth - displayCutout - systemBars
                }

                else -> {
                    @Suppress("DEPRECATION")
                    val currentDisplay = windowManager?.defaultDisplay
                    val appUsableSize = Point()
                    @Suppress("DEPRECATION")
                    currentDisplay?.apply {
                        getSize(appUsableSize)
                    }

                    appUsableSize.x
                }
            }
        }

    internal val Context.appHeight: Int
        get() {
            val windowManager = getSystemService<WindowManager>()

            return when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> {
                    val systemBars = windowManager
                        ?.currentWindowMetrics
                        ?.windowInsets
                        ?.getInsets(WindowInsets.Type.systemBars())
                        ?.let { it.bottom + it.top }
                        ?: 0

                    screenHeight - systemBars
                }

                else -> {
                    @Suppress("DEPRECATION")
                    val currentDisplay = windowManager?.defaultDisplay
                    val appUsableSize = Point()
                    @Suppress("DEPRECATION")
                    currentDisplay?.apply {
                        getSize(appUsableSize)
                    }

                    appUsableSize.y - statusBarHeight
                }
            }
        }

    private val Context.screenHeight: Int
        get() {
            val windowManager = getSystemService<WindowManager>()
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                windowManager?.currentWindowMetrics?.bounds?.height() ?: 0
            } else {
                val displayMetrics = DisplayMetrics()
                @Suppress("DEPRECATION")
                windowManager?.defaultDisplay?.getRealMetrics(displayMetrics)
                displayMetrics.heightPixels
            }
        }

    private val Context.screenWidth: Int
        get() {
            val windowManager = getSystemService<WindowManager>()
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                windowManager?.currentWindowMetrics?.bounds?.width() ?: 0
            } else {
                val displayMetrics = DisplayMetrics()
                @Suppress("DEPRECATION")
                windowManager?.defaultDisplay?.getRealMetrics(displayMetrics)
                displayMetrics.widthPixels
            }
        }

    internal val Context.portrait: Boolean
        get() = when (resources.configuration.orientation) {
            ORIENTATION_PORTRAIT -> true
            else -> false
        }

    private val Context.statusBarHeight: Int
        @SuppressLint("DiscouragedApi", "InternalInsetResource")
        get() {
            var result = 0
            val resourceId: Int = resources.getIdentifier("status_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = resources.getDimensionPixelSize(resourceId)
            }
            return result
        }
}