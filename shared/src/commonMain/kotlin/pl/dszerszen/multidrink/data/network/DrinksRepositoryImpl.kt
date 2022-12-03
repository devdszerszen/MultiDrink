package pl.dszerszen.multidrink.data.network

import pl.dszerszen.multidrink.data.network.mapper.toDomain
import pl.dszerszen.multidrink.db.DrinksDatabase
import pl.dszerszen.multidrink.domain.Error
import pl.dszerszen.multidrink.domain.Response
import pl.dszerszen.multidrink.domain.model.Drink
import pl.dszerszen.multidrink.domain.repository.DrinksRepository

class DrinksRepositoryImpl(
    private val database: DrinksDatabase,
    private val api: DrinksApi
) : DrinksRepository {

    override suspend fun getRandomDrink(): Drink {
        return api.getRandom().toDomain()
    }

    override suspend fun findByName(name: String): Response<List<Drink>> {
        return fetchCatching {
            api.getByName(name).map { it.toDomain() }
        }
    }

    private suspend fun <T> fetchCatching(action: suspend () -> T): Response<T> {
        return try {
            Response.Success(action())
        } catch (e: Exception) {
            Response.Failure(Error.NetworkError(e.message ?: "Unknown error"))
        }
    }
}