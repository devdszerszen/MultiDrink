package pl.dszerszen.multidrink.data.network

import pl.dszerszen.multidrink.data.network.mapper.toDomain
import pl.dszerszen.multidrink.db.DrinksDatabase
import pl.dszerszen.multidrink.domain.model.Drink
import pl.dszerszen.multidrink.domain.repository.DrinksRepository

class DrinksRepositoryImpl(
    private val database: DrinksDatabase,
    private val api: DrinksApi
) : DrinksRepository {

    override suspend fun getRandomDrink(): Drink {
        return api.getRandom().toDomain()
    }
}