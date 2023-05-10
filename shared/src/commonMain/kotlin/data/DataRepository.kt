package data

import arrow.core.Either
import arrow.core.right
import data.cloud.CloudDataSource
import data.model.AccountInfo

class DataRepository(
    private val cloudDataSource: CloudDataSource
) {

    suspend fun getAccountInfo(id: Int): Either<Exception, AccountInfo> {
        val accInfo = cloudDataSource.getAccountInfo()
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