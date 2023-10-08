package com.np.kmm_test.data

import com.np.kmm_test.domain.SpeakingMlResult
import com.np.kmm_test.domain.SpeakingRepository
import com.np.kmm_test.utils.FileSystemCore
import com.np.kmm_test.utils.runSuspendCatching
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import okio.Path.Companion.toPath

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
        runSuspendCatching {
            val source = FileSystemCore.source(path.toPath())

            api.getSpeakingResult(
                courseId = courseId,
                lessonId = lessonId,
                quizId = quizId,
                file = source,
            ).toDomain()
        }
    }
}
