package data.model

import kotlinx.datetime.LocalDateTime

data class Transaction(
    val id: Int,
    val dateTime: LocalDateTime,
    val amount: Float,
    val comment: String
)