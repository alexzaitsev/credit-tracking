package data

import data.model.Transaction
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

private val tz = TimeZone.of("America/Edmonton")

fun getMockCIBCOksanaTxs() =
    listOf(
        Transaction(
            id = 0,
            dateTime = Instant.fromEpochMilliseconds(1682042400000)
                .toLocalDateTime(tz), // 2023-04-20 20:00:00
            amount = -100.0f,
            comment = ""
        ),
        Transaction(
            id = 1,
            dateTime = Instant.fromEpochMilliseconds(1682125260000)
                .toLocalDateTime(tz), // 2023-04-21 19:01:00
            amount = -100.0f,
            comment = "winners"
        ),
        Transaction(
            id = 2,
            dateTime = Instant.fromEpochMilliseconds(1682128320000)
                .toLocalDateTime(tz), // 2023-04-21 19:52:00
            amount = +300.0f,
            comment = ""
        )
    ).sortedByDescending { it.id }
