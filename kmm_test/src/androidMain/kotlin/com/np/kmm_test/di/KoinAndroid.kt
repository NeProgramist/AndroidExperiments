package com.np.kmm_test.di

import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<Settings.Factory> {
        SharedPreferencesSettings.Factory(get())
    }

    single {
        OkHttp.create()
    }
}
