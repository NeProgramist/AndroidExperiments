package com.np.kmm_test.domain

import co.touchlab.kermit.Logger
import com.github.kittinunf.result.Result
import com.np.kmm_test.utils.FileSystemCore
import okio.Path.Companion.toPath

enum class PartLabel {
    CORRECT,
    INCORRECT,
}

data class SpeakingAnalyzeResult(
    val correct: Boolean,
    val score: Int,
    val parts: List<SentencePart>,
)

class AnalyzeAudioUseCase(
    private val repository: SpeakingRepository,
    private val logger: Logger,
) {
    suspend operator fun invoke(
        courseId: String,
        lessonId: Long,
        quizId: Long,
        path: String,
    ): Result<SpeakingAnalyzeResult, Exception> {
        val res = repository.getSpeakingResult(
            courseId = courseId,
            lessonId = lessonId,
            quizId = quizId,
            path = path,
        )

        if (res is Result.Success) {
            val speakingResult = res.value

            val percents = speakingResult.score
            val sentence = speakingResult.sentence

            val textParts = mutableListOf<SentencePart>()

            val resParts = speakingResult.parts.toMutableList()
            var latest: SentencePart.Text? = null

            var source = speakingResult.sentence

            while (resParts.isNotEmpty()) {
                val curPart = resParts.first()
                val label = if (curPart.correct) PartLabel.CORRECT else PartLabel.INCORRECT

                when (val index = source.indexOf(curPart.part)) {
                    // shouldn't be reached with consistent content
                    -1 -> {
                        break
                    }

                    /*
                    this mean that there is no punctuation or space before this
                    word part, so we can just add it to the latest word part or to the
                    result list
                 */
                    0 -> {

                        val offset = sentence.length - source.length

                        val wp = SentencePart.Text(
                            value = curPart.part,
                            label = label,
                            start = offset,
                            end = offset + curPart.part.length - 1,
                        )

                        if (latest?.label == label) {
                            latest += wp
                        } else {
                            latest?.let { textParts += it }
                            latest = wp
                        }

                        source = source.removeRange(0, curPart.part.length)
                        resParts.removeFirst()
                    }

                    else -> {
                        val offset = index + sentence.length - source.length

                        val wp = SentencePart.Text(
                            value = curPart.part,
                            label = label,
                            start = offset,
                            end = offset + curPart.part.length - 1,
                        )

                        latest?.let { textParts += it }
                        textParts += SentencePart.Punctuation(
                            value = source.slice(0 until index),
                        )
                        latest = wp

                        source = source.removeRange(0, index + curPart.part.length)
                        resParts.removeFirst()
                    }
                }
            }
            latest?.let { textParts += it }
            if (source.isNotEmpty()) {
                textParts += SentencePart.Punctuation(
                    value = source,
                )
            }

            return Result.success(
                SpeakingAnalyzeResult(
                    correct = speakingResult.correct,
                    score = (percents * 100).toInt().coerceIn(0, 100),
                    parts = textParts,
                )
            )
        } else {
            return Result.failure(res.failure())
        }
    }

    fun testDirectory(path: String): Boolean {
        val files = FileSystemCore.listOrNull(path.toPath())
        files?.forEach {
            logger.d("testDirectory: $it")
        }

        return FileSystemCore.exists(path.toPath(true))
    }
}
