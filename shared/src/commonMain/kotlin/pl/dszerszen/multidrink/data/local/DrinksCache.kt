package pl.dszerszen.multidrink.data.local

import pl.dszerszen.multidrink.domain.model.Drink

object DrinksCache {
    private val cachedList = mutableMapOf<String, Drink>()

    fun store(drinks: List<Drink>) {
        cachedList.putAll(drinks.associateBy { it.id })
    }

    fun fetch(drinkId: String) : Drink? {
        return cachedList[drinkId]
    }
}