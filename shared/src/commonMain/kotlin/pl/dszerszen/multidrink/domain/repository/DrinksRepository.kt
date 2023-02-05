package pl.dszerszen.multidrink.domain.repository

import pl.dszerszen.multidrink.domain.SuspendWrapper
import pl.dszerszen.multidrink.domain.model.Drink

interface DrinksRepository {
    fun getRandomDrink(): SuspendWrapper<Drink>
    fun findByName(name: String): SuspendWrapper<List<Drink>>
    fun findById(id: String): SuspendWrapper<Drink>
}