package com.np.kmm_test.di

import com.np.kmm_test.data.SpeakingApi
import com.np.kmm_test.data.SpeakingApiImpl
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.datetime.Clock
import kotlinx.serialization.json.Json
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

// move to core module
fun initKoin(appModule: Module): KoinApplication = startKoin {
    modules(
        appModule,
        platformModule,
        coreModule,
    )
}

private val coreModule get() = module {
    single<Clock> {
        Clock.System
    }

    single {
        val engine = get<HttpClientEngine>()
        val log = get<co.touchlab.kermit.Logger>()
        HttpClient(engine) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) = log.v { message }
                }
                level = LogLevel.INFO
            }
        }
    }

    single<SpeakingApi> {
        SpeakingApiImpl(get())
    }
}

expect val platformModule: Module
