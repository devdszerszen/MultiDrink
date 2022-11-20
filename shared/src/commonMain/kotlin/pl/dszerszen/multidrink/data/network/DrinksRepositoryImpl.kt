package pl.dszerszen.multidrink.data.network

import pl.dszerszen.multidrink.domain.model.Drink
import pl.dszerszen.multidrink.domain.repository.DrinksRepository

class DrinksRepositoryImpl : DrinksRepository {

    private val api = DrinksApi()

    override suspend fun getRandomDrink(): Drink {
        return api.getRandom()
    }
}