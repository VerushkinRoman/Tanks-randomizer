package com.posse.tanksrandomizer.conventionplugins.base.extensions

import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.DependencyHandlerScope

fun <T : Any> DependencyHandlerScope.implementation(dependency: Provider<T>) {
    add("implementation", dependency)
}

@Suppress("unused")
fun <T : Any> DependencyHandlerScope.debugImplementation(dependency: Provider<T>) {
    add("debugImplementation", dependency)
}
