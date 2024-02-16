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
                    "eyJhbGciOiJSUzI1NiIsImtpZCI6ImFlYzU4NjcwNGNhOTZiZDcwMzZiMmYwZDI4MGY5NDlmM2E5NzZkMzgiLCJ0eXAiOiJKV1QifQ.eyJyb2xlIjpbInRlc3RpbmciXSwiaXNzIjoiaHR0cHM6Ly9zZWN1cmV0b2tlbi5nb29nbGUuY29tL3Rlbi13b3JkcyIsImF1ZCI6InRlbi13b3JkcyIsImF1dGhfdGltZSI6MTcwNzg1MjQ1MiwidXNlcl9pZCI6ImlHM2JLdmJWNVVoOUZVa1pyMWx2bDZpS0JlSzIiLCJzdWIiOiJpRzNiS3ZiVjVVaDlGVWtacjFsdmw2aUtCZUsyIiwiaWF0IjoxNzA3ODUyNDUyLCJleHAiOjE3MDc4NTYwNTIsImVtYWlsIjoicXFxQGcuY28iLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImZpcmViYXNlIjp7ImlkZW50aXRpZXMiOnsiZW1haWwiOlsicXFxQGcuY28iXX0sInNpZ25faW5fcHJvdmlkZXIiOiJwYXNzd29yZCJ9fQ.Q5PdeRNzvOnJAR2QKdJypRC1IQm9Xs51ikmwDSEeLc8sTOosPkqLWaO_sDjx4Vi0nFz4FHjZUtbXqm4n_JJSN5Al8urne0BlCrs3uQpfot1MZ2_v4YjpMEmqr7h51FWLrqHmqBQOHesdixodGjCroiEV_d0BEkor5v_m31Zv5BE5faNp3NPihSvz3DJbAgY2UilOkZZX35kHdLZf18VIfg2X2j4W6MBRqh2Ui_HdhYjZhzzNPgX1jQS-jKlPGaeoep6uyKG7hR9iJXdAAKp4pJVrcdZpRvBRkEboUvn5rCWJcC3jmQn-rxBdeK84GHzS2sfHPENF3wjPI5X0WLPMiA",
                )
            }
            viewModel { MainViewModel(get(), get()) }
        }

        initKoin(appModule)
    }
}
