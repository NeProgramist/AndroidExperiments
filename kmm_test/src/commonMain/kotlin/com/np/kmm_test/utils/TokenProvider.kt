package com.np.kmm_test.utils

interface TokenProvider {
    suspend fun requireToken(): String
}

data class TemporaryTokenProvider(val token: String) : TokenProvider {
    override suspend fun requireToken(): String = token
}
