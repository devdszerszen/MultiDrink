package pl.dszerszen.multidrink.domain.repository

import pl.dszerszen.multidrink.domain.Response
import pl.dszerszen.multidrink.domain.model.Drink

interface DrinksRepository {
    suspend fun getRandomDrink(): Drink
    suspend fun findByName(name: String): Response<List<Drink>>
}