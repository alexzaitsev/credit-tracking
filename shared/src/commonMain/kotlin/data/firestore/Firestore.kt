package data.firestore

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

class Firestore {

    private val client = HttpClient()

    suspend fun greeting(): String {
        val response = client.get("https://ktor.io/docs/")
        return response.bodyAsText()
    }
}
