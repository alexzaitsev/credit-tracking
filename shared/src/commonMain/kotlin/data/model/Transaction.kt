package data.model

import kotlinx.datetime.LocalDateTime

data class Transaction(
    val id: Long,
    val dateTime: LocalDateTime,
    val amount: Float,
    val comment: String
)