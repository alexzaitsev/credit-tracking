package data.cloud

import data.cloud.model.CloudAccount
import data.cloud.model.CloudTx
import kotlinx.coroutines.flow.Flow

class CloudDataSource(private val cloudRealm: CloudRealm) {

    suspend fun observeAccounts(): Flow<List<CloudAccount>> = cloudRealm.readCollection()

    suspend fun addTx(cloudTx: CloudTx) = cloudRealm.save(cloudTx)
}
