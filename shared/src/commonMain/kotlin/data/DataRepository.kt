package data

import arrow.core.Either
import arrow.core.right
import data.firestore.FirestoreDataSource
import data.model.AccountInfo

class DataRepository(
    private val firestoreDataSource: FirestoreDataSource
) {

    suspend fun getAccountInfo(id: Int): Either<Exception, AccountInfo> {
        val accInfo = firestoreDataSource.getAccountInfo()
        println(accInfo)

//        val apiAccountInfo = apiDataSource.getAccountInfo()
//        val lastTxs = getMockCIBCOksanaAccountInfo()
//        return AccountInfo(
//            bankName = apiAccountInfo.bankName,
//            balance = apiAccountInfo.balance,
//            status = "All good",
//            lastTxs = lastTxs
//        ).right()
        return getMockCIBCOksanaAccountInfo().right()
    }
}