package data.cloud

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

val TIME_ZONE = TimeZone.of("America/Edmonton")
fun now() = Clock.System.now().toLocalDateTime(TIME_ZONE)

const val REALM_COLLECTION_ACCOUNT = "account"
const val REALM_COLLECTION_TX = "tx"