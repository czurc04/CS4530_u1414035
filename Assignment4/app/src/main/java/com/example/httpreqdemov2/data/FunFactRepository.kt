package com.example.httpreqdemov2.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow

class FunFactRepository(
    private val dao: FunFactDao,
    private val client: HttpClient
) {
    companion object {
        private const val RANDOM_FACT_URL = "https://uselessfacts.jsph.pl/random.json?language=en"
    }

    /** Local-only search. An empty query returns every saved fact. */
    fun searchFacts(query: String): Flow<List<FunFact>> = dao.search(query)

    /** Fetches a random fact from the API and saves it. Returns false if it was already saved. */
    suspend fun fetchAndSaveFunFact(): Boolean {
        val fact: FunFact = client.get(RANDOM_FACT_URL).body()
        return dao.insert(fact) != -1L
    }
}
