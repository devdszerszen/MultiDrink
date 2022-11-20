package pl.dszerszen.multidrink.domain.repository

import pl.dszerszen.multidrink.domain.model.Drink

interface DrinksRepository {
    suspend fun getRandomDrink(): Drink
}