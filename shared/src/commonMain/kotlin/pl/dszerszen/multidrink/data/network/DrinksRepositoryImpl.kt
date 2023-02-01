package pl.dszerszen.multidrink.data.network

import pl.dszerszen.multidrink.data.network.mapper.toDomain
import pl.dszerszen.multidrink.db.DrinksDatabase
import pl.dszerszen.multidrink.domain.AppException
import pl.dszerszen.multidrink.domain.handleSuspend
import pl.dszerszen.multidrink.domain.repository.DrinksRepository

class DrinksRepositoryImpl(
    private val database: DrinksDatabase,
    private val api: DrinksApi
) : DrinksRepository {

    override fun getRandomDrink() = handleSuspend {
        api.getRandom()?.toDomain() ?: throw AppException.NetworkException("Fetch error occurred")
    }

    override fun findByName(name: String) = handleSuspend {
        api.getByName(name)
            ?.map { it.toDomain() }
            .orEmpty()
    }
}