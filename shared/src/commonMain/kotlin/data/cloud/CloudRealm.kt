package data.cloud

import data.cloud.model.CloudAccount
import data.util.asCommonFlow
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.Credentials
import io.realm.kotlin.mongodb.sync.SyncConfiguration
import io.realm.kotlin.notifications.ResultsChange
import io.realm.kotlin.types.RealmObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class CloudRealm(private val realmApp: App) {

    var realm: Realm? = null

    private suspend fun init() {
        if (realm == null) {
            val user = realmApp.login(Credentials.anonymous())
            val config = SyncConfiguration
                .Builder(user, setOf(CloudAccount::class))
                .initialSubscriptions { realm ->
                    add(
                        query = realm.query<CloudAccount>(),
                        name = REALM_COLLECTION_ACCOUNT,
                        updateExisting = true
                    )
                }
                .build()
            realm = Realm.open(config)
        }
    }

    suspend fun <T> safeCall(block: suspend () -> T): T {
        init()
        return block()
    }

    suspend inline fun <reified T : RealmObject> read(): Flow<ResultsChange<T>> = safeCall {
        realm?.query<T>()?.asCommonFlow() ?: emptyFlow()
    }

    suspend fun <T : RealmObject> save(data: T) = safeCall {
        realm?.write {
            copyToRealm(data)
        }
    }
}