package data.firestore

import data.firestore.model.FirestoreAccountInfo
import kotlinx.coroutines.delay

class FirestoreDataSource(val firestore: Firestore) {

    suspend fun getAccountInfo(): FirestoreAccountInfo {
        println(firestore.greeting())
//        val db = Firebase.firestore
//        val alexCibcDocument = db.collection("account").document("alex-cibc").get()
//        val alexCibc = alexCibcDocument.data(FirestoreAccountInfo.serializer())
        delay(3000L)
        return FirestoreAccountInfo(
            bankName = "",
            personName = "",
            balance = 0f
        )
    }
}
