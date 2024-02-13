package com.np.kmm_test.domain

import com.github.kittinunf.result.Result

data class SpeakingMlResult(
    val correct: Boolean,
    val score: Float,
    val sentence: String,
    val parts: List<WordPart>,
) {

    data class WordPart(
        val part: String,
        val phoneme: String,
        val correct: Boolean,
        val score: Float,
    )
}

interface SpeakingRepository {
    suspend fun getSpeakingResult(
        courseId: String,
        lessonId: Long,
        quizId: Long,
        path: String,
    ): Result<SpeakingMlResult, Exception>
}
