package com.np.kmm_test.domain

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class SentencePartKtTest {

    @Test
    fun `should return correct result with proper params`() {
        // given
        val first = SentencePart.Text(
            value = "first",
            label = PartLabel.CORRECT,
            start = 0,
            end = 4,
        )

        val second = SentencePart.Text(
            value = "second",
            label = PartLabel.CORRECT,
            start = 5,
            end = 10,
        )

        // when
        val result = first + second

        // then
        assertEquals(
            expected = SentencePart.Text(
                value = "firstsecond",
                label = PartLabel.CORRECT,
                start = 0,
                end = 10,
            ),
            actual = result,
        )
    }

    @Test
    fun `should throw exception when labels are different`() {
        // given
        val first = SentencePart.Text(
            value = "first",
            label = PartLabel.CORRECT,
            start = 0,
            end = 4,
        )

        val second = SentencePart.Text(
            value = "second",
            label = PartLabel.INCORRECT,
            start = 5,
            end = 10,
        )

        // when
        val exception = try {
            first + second
            null
        } catch (e: Exception) {
            e
        }

        // then
        assertNotNull(actual = exception)
    }

    @Test
    fun `should throw exception when parts are not neighbors`() {
        // given
        val first = SentencePart.Text(
            value = "first",
            label = PartLabel.CORRECT,
            start = 0,
            end = 4,
        )

        val second = SentencePart.Text(
            value = "second",
            label = PartLabel.CORRECT,
            start = 6,
            end = 11,
        )

        // when
        val exception = try {
            first + second
            null
        } catch (e: Exception) {
            e
        }

        // then
        assertNotNull(actual = exception)
    }
}