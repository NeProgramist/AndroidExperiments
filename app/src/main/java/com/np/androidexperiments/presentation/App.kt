package com.np.androidexperiments.presentation

import android.app.Application
import android.content.Context
import com.np.kmm_test.di.initKoin
import com.np.kmm_test.utils.TemporaryTokenProvider
import com.np.kmm_test.utils.TokenProvider
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        val appModule = module {
            single<Context> { this@App }
            single<TokenProvider> {
                TemporaryTokenProvider(
"eyJhbGciOiJSUzI1NiIsImtpZCI6ImQ0OWU0N2ZiZGQ0ZWUyNDE0Nzk2ZDhlMDhjZWY2YjU1ZDA3MDRlNGQiLCJ0eXAiOiJKV1QifQ.eyJyb2xlIjpbInRlc3RpbmciXSwiaXNzIjoiaHR0cHM6Ly9zZWN1cmV0b2tlbi5nb29nbGUuY29tL3Rlbi13b3JkcyIsImF1ZCI6InRlbi13b3JkcyIsImF1dGhfdGltZSI6MTY5OTYzNzU0MiwidXNlcl9pZCI6ImlHM2JLdmJWNVVoOUZVa1pyMWx2bDZpS0JlSzIiLCJzdWIiOiJpRzNiS3ZiVjVVaDlGVWtacjFsdmw2aUtCZUsyIiwiaWF0IjoxNjk5NjM3NTQyLCJleHAiOjE2OTk2NDExNDIsImVtYWlsIjoicXFxQGcuY28iLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImZpcmViYXNlIjp7ImlkZW50aXRpZXMiOnsiZW1haWwiOlsicXFxQGcuY28iXX0sInNpZ25faW5fcHJvdmlkZXIiOiJwYXNzd29yZCJ9fQ.F77jQy7NyVUkB7nnoGT0eCtPlPz8efnGBHHrzkcYZTfZfHIJPdYcUJzdRkdnLceKlBtx3UpdHQwnrn-sM8FP7s_RePgIN_eyG4EjXf8XUwsTp8Z02Kb-G-T_JYS3lNYS2Xb83344cn5ALPsUjaTDxa9T-VsXYwwjq81Ez6_AUXrQWSwTAkLHCZKWvNBThlWpEiRD4TxsAG2bqXuF_GzWE_LwOe29reSm72dLGWl-VHrgQ4YQHeeQDLI0vjfyA13K8Sn1-6aEovj3Hn3nFBRexm5acBM02E5bnNDZW7qW6TafP3e1Wtt8pWQI0MV0G44dG6XpA6iOZ_oEfnKGdScpPQ"
                )
            }
            viewModel { MainViewModel(get()) }
        }

        initKoin(appModule)
    }
}
