package data

import data.model.Account
import data.model.Tx
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

private val tz = TimeZone.of("America/Edmonton")

fun getMockCIBCOksanaAccountInfo() = Account(
    bankName = "CIBC",
    personName = "Oksana",
    balance = 100.0,
    lastTxs = getMockCIBCOksanaTxs()
)

fun getMockCIBCOksanaTxs() =
    listOf(
        Tx(
            id = 0,
            dateTime = Instant.fromEpochMilliseconds(1682042400000)
                .toLocalDateTime(tz), // 2023-04-20 20:00:00
            amount = -100.0,
            comment = ""
        ),
        Tx(
            id = 1,
            dateTime = Instant.fromEpochMilliseconds(1682125260000)
                .toLocalDateTime(tz), // 2023-04-21 19:01:00
            amount = -100.0,
            comment = "winners"
        ),
        Tx(
            id = 2,
            dateTime = Instant.fromEpochMilliseconds(1682128320000)
                .toLocalDateTime(tz), // 2023-04-21 19:52:00
            amount = +300.0,
            comment = ""
        )
    ).sortedByDescending { it.id }
