package com.np.kmm_test.data

import co.touchlab.kermit.Logger
import com.np.kmm_test.domain.SpeakingMlResult
import com.np.kmm_test.domain.SpeakingRepository
import com.np.kmm_test.utils.FileSystemCore
import com.np.kmm_test.utils.runSuspendCatching
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import okio.Path.Companion.toPath
import com.github.kittinunf.result.Result
import okio.FileSystem
import okio.IOException

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
    private val logger: Logger,
) : SpeakingRepository {

    override suspend fun getSpeakingResult(
        courseId: String,
        lessonId: Long,
        quizId: Long,
        path: String,
    ) = withContext(Dispatchers.IO) {
        Result.of<SpeakingMlResult, Exception> {
            logger.d("getSpeakingResult: $path")
            val path = path.toPath()
            val documents = "/Users/yzasko/Library/Developer/CoreSimulator/Devices/4FCEFBD9-04BF-4D0E-BA28-A1F711CB564B/data/Containers/Data/Application/2F27A159-EA57-47F9-A492-42CAAB8C7DCB/Documents"

            logger.d("getSpeakingResult: 2 $path")
            logger.d("getSpeakingResult: 3 ${FileSystemCore.listOrNull("/Users/yzasko/Library/Developer/CoreSimulator/Devices/4FCEFBD9-04BF-4D0E-BA28-A1F711CB564B/".toPath())}")
            logger.d("getSpeakingResult: 3 ${FileSystemCore.listOrNull("/Users/yzasko/Library/Developer/CoreSimulator/Devices/4FCEFBD9-04BF-4D0E-BA28-A1F711CB564B/data/Containers/Data/".toPath())}")
            logger.d("getSpeakingResult: 3 ${FileSystemCore.listOrNull("/Users/yzasko/Library/Developer/CoreSimulator/Devices/4FCEFBD9-04BF-4D0E-BA28-A1F711CB564B/data/Containers/Data/Application/2F27A159-EA57-47F9-A492-42CAAB8C7DCB/".toPath())}")
            logger.d("getSpeakingResult: 4 ${FileSystemCore.metadata(".".toPath())}")

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
