package data

import data.firestore.Firestore
import data.firestore.FirestoreDataSource
import io.ktor.client.HttpClient
import org.koin.dsl.module

val dataModule = module {

    single { HttpClient() }
    single { Firestore() }
    factory { FirestoreDataSource(get()) }
    factory { DataRepository(get()) }
}
