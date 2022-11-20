package pl.dszerszen.multidrink.data.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import pl.dszerszen.multidrink.domain.model.Drink
import pl.dszerszen.multidrink.domain.model.DrinkResponse

class DrinksApi {
    private val httpClient = HttpClient{
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = true
                prettyPrint = true
            })
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
        }
    }

    suspend fun getRandom(): Drink {
        return httpClient.get(BASE_URL + "random.php").body<DrinkResponse>().drinks.first()
    }

    companion object {
        private const val API_KEY = 1
        private const val BASE_URL = "https://thecocktaildb.com/api/json/v1/$API_KEY/"
    }
}