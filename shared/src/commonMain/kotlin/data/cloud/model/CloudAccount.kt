package data.cloud.model

import data.cloud.REALM_COLLECTION_ACCOUNT
import data.model.Account
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PersistedName
import io.realm.kotlin.types.annotations.PrimaryKey

@PersistedName(name = REALM_COLLECTION_ACCOUNT)
class CloudAccount : RealmObject {
    @PrimaryKey
    var _id: String = ""
    var bankName: String = ""
    var personName: String = ""
    var balance: Double = 0.0
}

fun CloudAccount.toData() = Account(
    id = _id,
    bankName = bankName,
    personName = personName,
    balance = balance,
    lastTxs = emptyList() // TODO stub
)

fun Account.toCloud(): CloudAccount {
    val account = this
    return CloudAccount().apply {
        _id = account.id
        bankName = account.bankName
        personName = account.personName
        balance = account.balance
    }
}
