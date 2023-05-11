package data

import REALM_APP_ID
import data.cloud.CloudDataSource
import data.cloud.CloudRealm
import io.realm.kotlin.log.LogLevel
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.AppConfiguration
import org.koin.dsl.module

val dataModule = module {

    single<App> {
        val configuration =
            AppConfiguration.Builder(REALM_APP_ID).log(LogLevel.ALL).build()
        App.create(configuration)
    }

    single { CloudRealm(get()) }
    factory { CloudDataSource(get()) }
    factory { DataRepository(get()) }
}
