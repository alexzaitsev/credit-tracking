package data.cloud

import data.cloud.model.CloudAccount
import data.model.Account
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CloudDataSource(private val cloudRealm: CloudRealm) {

    suspend fun observeAccounts(): Flow<List<Account>> =
        cloudRealm.readCollection<CloudAccount>().map { list ->
            list.map { cloudAccount ->
                Account(
                    bankName = cloudAccount.bankName,
                    personName = cloudAccount.personName,
                    balance = cloudAccount.balance,
                    lastTxs = emptyList() // TODO stub
                )
            }
        }
}
