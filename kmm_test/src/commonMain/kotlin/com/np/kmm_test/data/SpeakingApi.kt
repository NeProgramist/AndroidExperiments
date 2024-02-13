package com.np.kmm_test.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.InputProvider
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.append
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.post
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.quote
import io.ktor.utils.io.core.buildPacket
import io.ktor.utils.io.core.writeFully
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import okio.Source
import okio.buffer

interface SpeakingApi {
    suspend fun getSpeakingResult(
        courseId: String,
        lessonId: Long,
        quizId: Long,
        file: Source,
        fileName: String,
    ): SpeakingResultModel
}

class SpeakingApiImpl(
    private val client: HttpClient,
) : SpeakingApi {
    override suspend fun getSpeakingResult(
        courseId: String,
        lessonId: Long,
        quizId: Long,
        file: Source,
        fileName: String,
    ): SpeakingResultModel {
        val res = client.submitFormWithBinaryData(
            url = "https://courses.dev.promova.work/v1/users/speaking/${courseId}/${lessonId}/${quizId}",
            formData = formData {
                appendInput(key = "file", headers = Headers.build {
                    append(HttpHeaders.ContentType, "audio/wav")
                    append(HttpHeaders.ContentDisposition, "filename=${fileName.quote()}")
                }) {
                    buildPacket { writeFully(file.buffer().readByteArray()) }
                }
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
    val score: Float,
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
    val score: Float,
    @SerialName("word_part")
    val wordPart: String
)
