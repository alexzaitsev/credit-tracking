package data.cloud.model

import data.cloud.REALM_COLLECTION_TX
import data.cloud.now
import data.model.Tx
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PersistedName
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.datetime.LocalDateTime
import org.mongodb.kbson.ObjectId

@PersistedName(name = REALM_COLLECTION_TX)
class CloudTx : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var accountId: String = ""
    var dateTime: LocalDateTime = now()
    var amount: Double = 0.0
    var comment: String = ""
}

fun CloudTx.toData() = Tx(
    dateTime = dateTime,
    amount = amount,
    comment = comment
)

fun Tx.toCloud(accountId: String): CloudTx {
    val tx = this
    return CloudTx().apply {
        this.accountId = accountId
        dateTime = tx.dateTime
        amount = tx.amount
        comment = tx.comment
    }
}
