package data

import data.cloud.CloudDataSource
import data.cloud.model.toCloud
import data.model.Account
import data.model.Tx
import kotlinx.coroutines.flow.Flow

class DataRepository(
    private val cloudDataSource: CloudDataSource
) {

    suspend fun observeAccounts(): Flow<List<Account>> {
        return cloudDataSource.observeAccounts()
    }

    suspend fun addTx(tx: Tx, accountId: String) {
        cloudDataSource.addTx(tx.toCloud(accountId = accountId))
    }
}