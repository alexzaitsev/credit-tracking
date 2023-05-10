package data

import data.cloud.CloudDataSource
import org.koin.dsl.module

val dataModule = module {

    factory { CloudDataSource(get()) }
    factory { DataRepository(get()) }
}
