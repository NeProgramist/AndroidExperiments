package com.np.kmm_test.utils

import kotlin.coroutines.cancellation.CancellationException

/**
 *  https://github.com/Kotlin/kotlinx.coroutines/issues/1814
 */
/**
 * consider adding suspend to prevent calling from non-suspend functions
 */
inline fun <R> runSuspendCatching(block: () -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (c: CancellationException) {
        throw c
    } catch (e: Throwable) {
        Result.failure(e)
    }
}
