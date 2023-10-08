package com.np.kmm_test

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform