package com.np.kmm_test.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.quote
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import okio.Source
import okio.buffer

interface SpeakingApi {
    suspend fun getSpeakingResult(
        courseId: String,
        lessonId: String,
        quizId: String,
        file: Source,
    ): SpeakingResultModel
}

class SpeakingApiImpl(
    private val client: HttpClient,
) : SpeakingApi {
    override suspend fun getSpeakingResult(
        courseId: String,
        lessonId: String,
        quizId: String,
        file: Source,
    ): SpeakingResultModel {
        val res = client.submitFormWithBinaryData(
            url = "https://courses.dev.promova.work/v1/users/speaking/${courseId}/${lessonId}/${quizId}",
            formData = formData {
                append(
                    key = "audio".quote(),
                    value = file.buffer().readByteArray(),
                    headers = Headers.build {
                        append(HttpHeaders.ContentType, "audio/wav")
                    }
                )
            }
        )

        return res.body()
    }
}

@Serializable
data class SpeakingResultModel(
    @SerialName("is_correct")
    val isCorrect: Boolean,
    @SerialName("result")
    val result: List<WordPartModel>,
    @SerialName("score")
    val score: Double,
    @SerialName("sentence")
    val sentence: String
)

@Serializable
data class WordPartModel(
    @SerialName("is_correct")
    val isCorrect: Boolean,
    @SerialName("phoneme")
    val phoneme: String,
    @SerialName("score")
    val score: Double,
    @SerialName("word_part")
    val wordPart: String
)
