package data.model

import kotlinx.datetime.LocalDateTime

data class Tx(
    val dateTime: LocalDateTime,
    val amount: Double,
    val comment: String
)