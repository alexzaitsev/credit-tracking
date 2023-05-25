package data.cloud.model

import data.cloud.REALM_COLLECTION_TX
import data.cloud.TIME_ZONE
import data.model.Tx
import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PersistedName
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.datetime.Instant
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.mongodb.kbson.ObjectId

@PersistedName(name = REALM_COLLECTION_TX)
class CloudTx : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var accountId: String = ""
    var timestamp: RealmInstant = RealmInstant.now()
    var amount: Double = 0.0
    var comment: String = ""
}

fun CloudTx.toData() = Tx(
    dateTime = Instant.fromEpochSeconds(
        epochSeconds = timestamp.epochSeconds,
        nanosecondAdjustment = timestamp.nanosecondsOfSecond
    ).toLocalDateTime(TIME_ZONE),
    amount = amount,
    comment = comment
)

fun Tx.toCloud(accId: String): CloudTx {
    val tx = this
    val instant = tx.dateTime.toInstant(TIME_ZONE)
    return CloudTx().apply {
        accountId = accId
        timestamp = RealmInstant.from(
            epochSeconds = instant.epochSeconds,
            nanosecondAdjustment = instant.nanosecondsOfSecond
        )
        amount = tx.amount
        comment = tx.comment
    }
}
