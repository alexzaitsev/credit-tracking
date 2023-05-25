package data.cloud

import data.cloud.model.CloudAccount
import data.cloud.model.CloudTx
import data.cloud.model.toData
import data.model.Tx
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.Sort
import kotlinx.coroutines.flow.Flow

class CloudDataSource(private val cloudRealm: CloudRealm) {

    suspend fun observeAccounts(): Flow<List<CloudAccount>> = cloudRealm.readCollection()

    suspend fun getLastTx(accountId: String): List<Tx> = cloudRealm.safeCall { realm: Realm? ->
        realm?.query<CloudTx>("accountId == $0", accountId)
            ?.sort(property = "timestamp", sortOrder = Sort.DESCENDING)?.limit(5)?.find()
            ?: emptyList()
    }.map { cloudTx -> cloudTx.toData() }

    suspend fun addTx(cloudTx: CloudTx) = cloudRealm.save(cloudTx)
}
