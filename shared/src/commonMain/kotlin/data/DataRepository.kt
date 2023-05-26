package data

import data.source.cloud.CloudDataSource
import data.source.cloud.model.toCloud
import data.source.cloud.model.toData
import data.model.Account
import data.model.Tx
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataRepository(
    private val cloudDataSource: CloudDataSource
) {

    suspend fun observeAccounts(): Flow<List<Account>> {
        return cloudDataSource.observeAccounts().map { cloudAccounts ->
            cloudAccounts.map { it.toData() }
        }.map { accounts ->
            accounts.map { account: Account ->
                account.copy(
                    lastTxs = cloudDataSource.getLastTx(accountId = account.id).map { it.toData() }
                )
            }
        }
    }

    suspend fun addTx(tx: Tx, accountId: String) {
        cloudDataSource.addTx(tx.toCloud(accId = accountId))
    }
}