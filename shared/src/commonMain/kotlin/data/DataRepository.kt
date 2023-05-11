package data

import data.cloud.CloudDataSource
import data.model.Account
import kotlinx.coroutines.flow.Flow

class DataRepository(
    private val cloudDataSource: CloudDataSource
) {

    suspend fun observeAccounts(): Flow<List<Account>> {
        return cloudDataSource.observeAccounts()
    }
}