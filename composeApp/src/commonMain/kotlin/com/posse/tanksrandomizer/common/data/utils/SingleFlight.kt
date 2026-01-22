package com.posse.tanksrandomizer.common.data.utils

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class SingleFlight<T> {
    private val inFlight = mutableMapOf<String, Deferred<T>>()
    private val mutex = Mutex()

    suspend fun joinOrRun(
        key: String,
        block: suspend () -> T
    ): T = coroutineScope {
        val deferred = mutex.withLock {
            inFlight[key] ?: async(start = CoroutineStart.LAZY) {
                try {
                    block()
                } finally {
                    mutex.withLock {
                        inFlight.remove(key)
                    }
                }
            }.also { deferred ->
                inFlight[key] = deferred
            }
        }

        deferred.await()
    }
}
