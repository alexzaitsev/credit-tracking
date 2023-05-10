package data.cloud

import kotlinx.coroutines.delay

class CloudDataSource {

    suspend fun getAccountInfo(): FirestoreAccountInfo {
        println(firestore.test())
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
