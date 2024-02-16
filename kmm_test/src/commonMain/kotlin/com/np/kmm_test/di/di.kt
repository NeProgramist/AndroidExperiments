package com.np.kmm_test.di

import co.touchlab.kermit.StaticConfig
import co.touchlab.kermit.platformLogWriter
import com.np.kmm_test.data.SpeakingApi
import com.np.kmm_test.data.SpeakingApiImpl
import com.np.kmm_test.data.SpeakingRepositoryImpl
import com.np.kmm_test.domain.AnalyzeAudioUseCase
import com.np.kmm_test.domain.SpeakingRepository
import com.np.kmm_test.utils.TokenProvider
import com.np.kmm_test.utils.getWith
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.HttpHeaders
import io.ktor.http.headers
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

private val coreModule
    get() = module {
        single<Clock> {
            Clock.System
        }

        val baseLogger = co.touchlab.kermit.Logger(
            config = StaticConfig(
                logWriterList = listOf(platformLogWriter())
            ),
            tag = "AndroidExperiments",
        )
        factory { (tag: String?) -> if (tag != null) baseLogger.withTag(tag) else baseLogger }


        single {
            val engine = get<HttpClientEngine>()
            val log = getWith<co.touchlab.kermit.Logger>("HttpClient")
            HttpClient(engine) {
                install(Auth) {
                    bearer {
                        val tokenProvider = get<TokenProvider>()
                        loadTokens {
                            val token = tokenProvider.requireToken()
                            BearerTokens(accessToken = token, refreshToken = "")
                        }
                    }
                }
                install(ContentNegotiation) {
                    json(Json {
                        ignoreUnknownKeys = true
                    })
                }
                install(Logging) {
                    logger = object : Logger {
                        override fun log(message: String) = log.v { message }
                    }
                    level = LogLevel.ALL
                }
            }
        }

        single<SpeakingApi> {
            SpeakingApiImpl(get())
        }

        single<SpeakingRepository> {
            SpeakingRepositoryImpl(get(), getWith<co.touchlab.kermit.Logger>("SpeakingRepository"))
        }

        single<AnalyzeAudioUseCase> {
            AnalyzeAudioUseCase(get(), getWith<co.touchlab.kermit.Logger>("AnalyzeAudioUseCase"))
        }
    }

expect val platformModule: Module
