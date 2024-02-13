package com.np.kmm_test.data

import com.np.kmm_test.domain.SpeakingMlResult
import com.np.kmm_test.domain.SpeakingRepository
import com.np.kmm_test.utils.FileSystemCore
import com.np.kmm_test.utils.runSuspendCatching
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import okio.Path.Companion.toPath
import com.github.kittinunf.result.Result

fun SpeakingResultModel.toDomain() = SpeakingMlResult(
    correct = isCorrect,
    score = score,
    sentence = sentence,
    parts = result.map {
        SpeakingMlResult.WordPart(
            part = it.wordPart,
            phoneme = it.phoneme,
            score = it.score,
            correct = it.isCorrect,
        )
    },
)

class SpeakingRepositoryImpl(
    private val api: SpeakingApi,
) : SpeakingRepository {

    override suspend fun getSpeakingResult(
        courseId: String,
        lessonId: Long,
        quizId: Long,
        path: String,
    ) = withContext(Dispatchers.IO) {
        Result.of<SpeakingMlResult, Exception> {
            val path = path.toPath()
            val source = FileSystemCore.source(path)

            api.getSpeakingResult(
                courseId = courseId,
                lessonId = lessonId,
                quizId = quizId,
                file = source,
                fileName = path.name,
            ).toDomain()
        }
    }
}
