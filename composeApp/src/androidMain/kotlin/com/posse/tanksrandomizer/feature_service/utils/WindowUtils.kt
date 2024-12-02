package com.posse.tanksrandomizer.feature_service.utils

import android.graphics.PixelFormat
import android.graphics.Point
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowInsets
import android.view.WindowManager

internal object WindowUtils {
    internal fun getNavigationBarHeight(windowManager: WindowManager?): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            windowManager
                ?.currentWindowMetrics
                ?.windowInsets
                ?.getInsets(WindowInsets.Type.navigationBars())
                ?.bottom
                ?: 0
        } else {
            @Suppress("DEPRECATION")
            val currentDisplay = windowManager?.defaultDisplay
            val appUsableSize = Point()
            val realScreenSize = Point()
            @Suppress("DEPRECATION")
            currentDisplay?.apply {
                getSize(appUsableSize)
                getRealSize(realScreenSize)
            }

            // navigation bar on the side
            if (appUsableSize.x < realScreenSize.x) {
                return realScreenSize.x - appUsableSize.x
            }

            // navigation bar at the bottom
            return if (appUsableSize.y < realScreenSize.y) {
                realScreenSize.y - appUsableSize.y
            } else 0
        }
    }

    internal fun getDisplayCutoutSize(windowManager: WindowManager?): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            windowManager
                ?.currentWindowMetrics
                ?.windowInsets
                ?.getInsets(WindowInsets.Type.displayCutout())
                ?.let { it.left + it.right }
                ?: 0
        } else {
            @Suppress("DEPRECATION")
            val currentDisplay = windowManager?.defaultDisplay
            val appUsableSize = Point()
            val realScreenSize = Point()
            @Suppress("DEPRECATION")
            currentDisplay?.apply {
                getSize(appUsableSize)
                getRealSize(realScreenSize)
            }

            // navigation bar on the side TODO
            if (appUsableSize.x < realScreenSize.x) {
                return realScreenSize.x - appUsableSize.x
            }

            // navigation bar at the bottom TODO
            return if (appUsableSize.y < realScreenSize.y) {
                realScreenSize.y - appUsableSize.y
            } else 0
        }
    }

    internal fun getScreenHeight(windowManager: WindowManager?): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            windowManager?.currentWindowMetrics?.bounds?.height() ?: 0
        } else {
            val displayMetrics = DisplayMetrics()
            @Suppress("DEPRECATION")
            windowManager?.defaultDisplay?.getRealMetrics(displayMetrics)
            displayMetrics.heightPixels
        }
    }

    internal fun getScreenWidth(windowManager: WindowManager?): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            windowManager?.currentWindowMetrics?.bounds?.width() ?: 0
        } else {
            val displayMetrics = DisplayMetrics()
            @Suppress("DEPRECATION")
            windowManager?.defaultDisplay?.getRealMetrics(displayMetrics)
            displayMetrics.widthPixels
        }
    }

    internal val defaultLayoutParams = WindowManager.LayoutParams(
        /* w = */ WindowManager.LayoutParams.MATCH_PARENT,
        /* h = */ WindowManager.LayoutParams.MATCH_PARENT,
        /* _type = */ if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            @Suppress("DEPRECATION")
            WindowManager.LayoutParams.TYPE_PHONE
        },
        /* _flags = */ WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
        /* _format = */ PixelFormat.TRANSLUCENT,
    )
}