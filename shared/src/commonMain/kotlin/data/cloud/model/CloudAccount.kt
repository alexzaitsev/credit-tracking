package data.cloud.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PersistedName
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

@PersistedName(name = "account")
class CloudAccount : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    var bankName: String = ""
    var personName: String = ""
    var balance: Float = 0f
}