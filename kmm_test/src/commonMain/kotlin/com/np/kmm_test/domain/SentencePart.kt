package com.np.kmm_test.domain

sealed class SentencePart {
    abstract val value: String

    data class Punctuation(override val value: String) : SentencePart()

    /**
     * both [start] and [end] are inclusive
     */
    data class Text(
        override val value: String,
        val label: PartLabel,
        val start: Int,
        val end: Int,
    ) : SentencePart()
}

operator fun SentencePart.Text?.plus(
    other: SentencePart.Text,
): SentencePart.Text {
    if (this == null) return other

    require(label == other.label) {
        "to add word parts they should have same labels: $this - $other"
    }

    require(end + 1 == other.start) {
        "to add word parts they should be neighbors: $this - $other"
    }

    return SentencePart.Text(
        value = value + other.value,
        label = other.label,
        start = start,
        end = other.end,
    )
}
