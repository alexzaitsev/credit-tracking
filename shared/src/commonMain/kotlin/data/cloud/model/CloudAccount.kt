package data.cloud.model

import data.cloud.REALM_COLLECTION_ACCOUNT
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PersistedName
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

@PersistedName(name = REALM_COLLECTION_ACCOUNT)
class CloudAccount : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var bankName: String = ""
    var personName: String = ""
    var balance: Double = 0.0
}