package com.np.kmm_test.di

import com.np.kmm_test.utils.TokenProvider
import com.russhwolf.settings.NSUserDefaultsSettings
import com.russhwolf.settings.Settings
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.module.Module
import org.koin.dsl.module

fun initIOSKoin(tokenProvider: TokenProvider) = initKoin(module {
    single<TokenProvider> { tokenProvider }
})

actual val platformModule: Module = module {
    single<Settings.Factory> {
        NSUserDefaultsSettings.Factory()
    }

    single {
        Darwin.create()
    }
}
