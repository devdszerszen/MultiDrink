package pl.dszerszen.multidrink.data.network

import pl.dszerszen.multidrink.data.local.DrinksCache
import pl.dszerszen.multidrink.data.network.mapper.toDomain
import pl.dszerszen.multidrink.db.DrinksDatabase
import pl.dszerszen.multidrink.domain.handleSuspend
import pl.dszerszen.multidrink.domain.repository.DrinksRepository

class DrinksRepositoryImpl(
    private val database: DrinksDatabase,
    private val api: DrinksApi,
    private val cache: DrinksCache
) : DrinksRepository {

    override fun getRandomDrink() = handleSuspend {
        api.getRandom().toDomain()
    }

    override fun findByName(name: String) = handleSuspend {
        api.getByName(name)
            ?.map { it.toDomain() }
            .orEmpty().also {
                cache.store(it)
            }
    }

    override fun findById(id: String) = handleSuspend {
        cache.fetch(id) ?: api.getById(id).toDomain()
    }
}