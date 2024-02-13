package com.np.kmm_test.domain

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import com.github.kittinunf.result.Result
import com.github.kittinunf.result.isFailure

class TestSpeakingRepository(
    private val result: Result<SpeakingMlResult, Exception>,
) : SpeakingRepository {
    override suspend fun getSpeakingResult(
        courseId: String,
        lessonId: Long,
        quizId: Long,
        path: String
    ): Result<SpeakingMlResult, Exception> {
        return result
    }
}

class AnalyzeAudioUseCaseTest {

    @Test
    fun `should return failure when repository returns failure`() = runTest {
        // given
        val repository = TestSpeakingRepository(Result.failure(Exception("Test")))
        val useCase = AnalyzeAudioUseCase(repository)

        // when
        val res = useCase("courseId", 1, 1, "path")

        // then
        assertTrue(res.isFailure())
    }

    @Test
    fun `should return normalized score`() = runTest {
        // given
        val repository = TestSpeakingRepository(
            result = Result.success(
                SpeakingMlResult(
                    correct = false,
                    score = 0.22f,
                    sentence = "",
                    parts = listOf()
                )
            )
        )
        val useCase = AnalyzeAudioUseCase(repository)

        // when
        val res = useCase("courseId", 1, 1, "path")

        // then
        assertEquals(22, res.get().score)
    }

    @Test
    fun `should use correct value from result instead of checking by score`() = runTest {
        // given
        val repository = TestSpeakingRepository(
            result = Result.success(
                SpeakingMlResult(
                    correct = true,
                    score = 0f,
                    sentence = "",
                    parts = listOf()
                )
            )
        )
        val useCase = AnalyzeAudioUseCase(repository)

        // when
        val res = useCase("courseId", 1, 1, "path")

        // then
        assertTrue(res.get().correct)
    }

    @Test
    fun `should join word parts with the same label`() = runTest {
        // given
        val repository = TestSpeakingRepository(
            Result.success(
                SpeakingMlResult(
                    correct = true,
                    score = 0.5f,
                    sentence = "Helloworld",
                    parts = listOf(
                        SpeakingMlResult.WordPart(
                            part = "He",
                            phoneme = "He",
                            correct = true,
                            score = 1f,
                        ),
                        SpeakingMlResult.WordPart(
                            part = "llo",
                            phoneme = "llo",
                            correct = true,
                            score = 1f,
                        ),
                        SpeakingMlResult.WordPart(
                            part = "world",
                            phoneme = "world",
                            correct = true,
                            score = 1f,
                        ),
                    )
                )
            )
        )
        val useCase = AnalyzeAudioUseCase(repository)

        // when
        val res = useCase("courseId", 1, 1, "path")

        // then
        val expected = SpeakingAnalyzeResult(
            correct = true,
            score = 50,
            parts = listOf(
                SentencePart.Text(
                    value = "Helloworld",
                    label = PartLabel.CORRECT,
                    start = 0,
                    end = 9,
                ),
            )
        )
        assertEquals(expected, res.get())
    }

    @Test
    fun `shouldn't join word parts with the same label`() = runTest {
        // given
        val repository = TestSpeakingRepository(
            Result.success(
                SpeakingMlResult(
                    correct = true,
                    score = 0.5f,
                    sentence = "Helloworld",
                    parts = listOf(
                        SpeakingMlResult.WordPart(
                            part = "He",
                            phoneme = "He",
                            correct = true,
                            score = 1f,
                        ),
                        SpeakingMlResult.WordPart(
                            part = "llo",
                            phoneme = "llo",
                            correct = false,
                            score = 0f,
                        ),
                        SpeakingMlResult.WordPart(
                            part = "world",
                            phoneme = "world",
                            correct = true,
                            score = 1f,
                        ),
                    )
                )
            )
        )
        val useCase = AnalyzeAudioUseCase(repository)

        // when
        val res = useCase("courseId", 1, 1, "path")

        // then
        val expected = SpeakingAnalyzeResult(
            correct = true,
            score = 50,
            parts = listOf(
                SentencePart.Text(
                    value = "He",
                    label = PartLabel.CORRECT,
                    start = 0,
                    end = 1,
                ),
                SentencePart.Text(
                    value = "llo",
                    label = PartLabel.INCORRECT,
                    start = 2,
                    end = 4,
                ),
                SentencePart.Text(
                    value = "world",
                    label = PartLabel.CORRECT,
                    start = 5,
                    end = 9,
                ),
            )
        )
        assertEquals(expected, res.get())
    }

    @Test
    fun `should split by punctuation if it's not present in word part`() = runTest {
        // given
        val repository = TestSpeakingRepository(
            Result.success(
                SpeakingMlResult(
                    correct = true,
                    score = 0.5f,
                    sentence = "?Hello, world!",
                    parts = listOf(
                        SpeakingMlResult.WordPart(
                            part = "He",
                            phoneme = "He",
                            correct = true,
                            score = 1f,
                        ),
                        SpeakingMlResult.WordPart(
                            part = "llo",
                            phoneme = "llo",
                            correct = false,
                            score = 1f,
                        ),
                        SpeakingMlResult.WordPart(
                            part = "world",
                            phoneme = "world",
                            correct = true,
                            score = 1f,
                        ),
                    )
                )
            )
        )
        val useCase = AnalyzeAudioUseCase(repository)

        // when
        val res = useCase("courseId", 1, 1, "path")

        // then
        val expected = SpeakingAnalyzeResult(
            correct = true,
            score = 50,
            parts = listOf(
                SentencePart.Punctuation("?"),
                SentencePart.Text(
                    value = "He",
                    label = PartLabel.CORRECT,
                    start = 1,
                    end = 2,
                ),
                SentencePart.Text(
                    value = "llo",
                    label = PartLabel.INCORRECT,
                    start = 3,
                    end = 5,
                ),
                SentencePart.Punctuation(", "),
                SentencePart.Text(
                    value = "world",
                    label = PartLabel.CORRECT,
                    start = 8,
                    end = 12,
                ),
                SentencePart.Punctuation("!"),
            )
        )
        assertEquals(expected, res.get())
    }

    @Test
    fun `shouldn't split by punctuation if it's present in word part`() = runTest {
        // given
        val repository = TestSpeakingRepository(
            Result.success(
                SpeakingMlResult(
                    correct = true,
                    score = 0.5f,
                    sentence = "?Hello, world!",
                    parts = listOf(
                        SpeakingMlResult.WordPart(
                            part = "?He",
                            phoneme = "He",
                            correct = true,
                            score = 1f,
                        ),
                        SpeakingMlResult.WordPart(
                            part = "llo, ",
                            phoneme = "llo",
                            correct = false,
                            score = 1f,
                        ),
                        SpeakingMlResult.WordPart(
                            part = "world!",
                            phoneme = "world",
                            correct = true,
                            score = 1f,
                        ),
                    )
                )
            )
        )
        val useCase = AnalyzeAudioUseCase(repository)

        // when
        val res = useCase("courseId", 1, 1, "path")

        // then
        val expected = SpeakingAnalyzeResult(
            correct = true,
            score = 50,
            parts = listOf(
                SentencePart.Text(
                    value = "?He",
                    label = PartLabel.CORRECT,
                    start = 0,
                    end = 2,
                ),
                SentencePart.Text(
                    value = "llo, ",
                    label = PartLabel.INCORRECT,
                    start = 3,
                    end = 7,
                ),
                SentencePart.Text(
                    value = "world!",
                    label = PartLabel.CORRECT,
                    start = 8,
                    end = 13,
                ),
            )
        )
        assertEquals(expected, res.get())
    }

    @Test
    fun `should break from analyzing in case of inconsistent content`() = runTest {
        // given
        val repository = TestSpeakingRepository(
            Result.success(
                SpeakingMlResult(
                    correct = true,
                    score = 0.5f,
                    sentence = "Hello world",
                    parts = listOf(
                        SpeakingMlResult.WordPart(
                            part = "abc",
                            phoneme = "abc",
                            correct = true,
                            score = 1f,
                        ),
                    )
                )
            )
        )
        val useCase = AnalyzeAudioUseCase(repository)

        // when
        val res = useCase("courseId", 1, 1, "path")

        // then
        val expected = SpeakingAnalyzeResult(
            correct = true,
            score = 50,
            parts = listOf(
                SentencePart.Punctuation(
                    value = "Hello world",
                ),
            )
        )
        assertEquals(expected, res.get())
    }
}
