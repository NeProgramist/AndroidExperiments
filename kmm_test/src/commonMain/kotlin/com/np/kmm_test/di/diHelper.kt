package com.np.kmm_test.di

import com.np.kmm_test.domain.AnalyzeAudioUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object DiHelper : KoinComponent {
    val analyzeAudioUseCase by inject<AnalyzeAudioUseCase>()
}
