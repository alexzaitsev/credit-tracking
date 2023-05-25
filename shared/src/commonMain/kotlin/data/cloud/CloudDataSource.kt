package data.cloud

import data.cloud.model.CloudAccount
import data.cloud.model.CloudTx
import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.Sort
import kotlinx.coroutines.flow.Flow

class CloudDataSource(private val cloudRealm: CloudRealm) {

    suspend fun observeAccounts(): Flow<List<CloudAccount>> = cloudRealm.readCollection()

    suspend fun getLastTx(accountId: String): List<CloudTx> = cloudRealm.safeCall { realm: Realm? ->
        realm?.query<CloudTx>("accountId == $0", accountId)
            ?.sort(property = "timestamp", sortOrder = Sort.DESCENDING)?.limit(5)?.find()
            ?: emptyList()
    }

    suspend fun addTx(cloudTx: CloudTx): Unit = cloudRealm.safeCall { realm ->
        realm?.write {
            // save new tx
            copyToRealm(cloudTx)

            // update balance in account
            val cloudAcc = query<CloudAccount>("_id == $0", cloudTx.accountId).limit(1).find()[0]
            cloudAcc.apply {
                balance += cloudTx.amount
            }
            copyToRealm(cloudAcc, updatePolicy = UpdatePolicy.ALL)
        }
    }
}
