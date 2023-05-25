package data

import data.cloud.CloudDataSource
import data.cloud.model.toCloud
import data.cloud.model.toData
import data.model.Account
import data.model.Tx
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataRepository(
    private val cloudDataSource: CloudDataSource
) {

    suspend fun observeAccounts(): Flow<List<Account>> {
        return cloudDataSource.observeAccounts().map { list ->
            list.map { it.toData() }
        }
    }

    suspend fun addTx(tx: Tx, accountId: String) {
        cloudDataSource.addTx(tx.toCloud(accountId = accountId))
    }
}