package com.posse.tanksrandomizer.common.compose.components

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.navigation3.scene.Scene

fun <T : Any> getNavAnimation(
    selectedOrder: Int,
    previousSelectedOrder: Int,
    portrait: Boolean = true,
): AnimatedContentTransitionScope<Scene<T>>.() -> ContentTransform {
    return {
        when {
            selectedOrder > previousSelectedOrder -> if (portrait) forwardNavAnimation else upNavAnimation
            selectedOrder < previousSelectedOrder -> if (portrait) backwardNavAnimation else downNavAnimation
            else -> noTransitionAnimation
        }
    }
}

private val forwardNavAnimation = ContentTransform(
    targetContentEnter = slideIntoContainer(SlideDirection.Left),
    initialContentExit = slideOutOfContainer(SlideDirection.Left)
)

private val backwardNavAnimation = ContentTransform(
    targetContentEnter = slideIntoContainer(SlideDirection.Right),
    initialContentExit = slideOutOfContainer(SlideDirection.Right)
)

private val upNavAnimation = ContentTransform(
    targetContentEnter = slideIntoContainer(SlideDirection.Up),
    initialContentExit = slideOutOfContainer(SlideDirection.Up)
)

private val downNavAnimation = ContentTransform(
    targetContentEnter = slideIntoContainer(SlideDirection.Down),
    initialContentExit = slideOutOfContainer(SlideDirection.Down)
)

private val noTransitionAnimation = ContentTransform(
    targetContentEnter = EnterTransition.None,
    initialContentExit = ExitTransition.None
)

private fun slideIntoContainer(direction: SlideDirection): EnterTransition {
    return slideIn(
        animationSpec = getStandardAnimation()
    ) { size ->
        calculateOffset(direction, size)
    }
}

private fun slideOutOfContainer(direction: SlideDirection): ExitTransition {
    return slideOut(
        animationSpec = getStandardAnimation()
    ) { size ->
        calculateOffset(direction, size, false)
    }
}

private fun calculateOffset(
    direction: SlideDirection,
    size: IntSize,
    slideIn: Boolean = true
): IntOffset {
    return when (direction) {
        SlideDirection.Right -> IntOffset(x = size.width.let { if (slideIn) -it else it }, y = 0)
        SlideDirection.Left -> IntOffset(x = size.width.let { if (slideIn) it else -it }, y = 0)
        SlideDirection.Up -> IntOffset(x = 0, y = size.height.let { if (slideIn) it else -it })
        SlideDirection.Down -> IntOffset(x = 0, y = size.height.let { if (slideIn) -it else it })
    }
}

private enum class SlideDirection {
    Right, Left, Up, Down
}

private fun <T> getStandardAnimation() = tween<T>(ANIMATION_DURATION)

private const val ANIMATION_DURATION = 500
