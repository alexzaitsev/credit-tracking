package data.firestore

import data.firestore.model.FirestoreAccountInfo

class FirestoreDataSource {

    suspend fun getAccountInfo(): FirestoreAccountInfo {
//        val db = Firebase.firestore
//        val alexCibcDocument = db.collection("account").document("alex-cibc").get()
//        val alexCibc = alexCibcDocument.data(FirestoreAccountInfo.serializer())
        return alexCibc
    }
}
