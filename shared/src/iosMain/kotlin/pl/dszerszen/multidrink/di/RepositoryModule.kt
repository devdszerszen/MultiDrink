package pl.dszerszen.multidrink.di

import pl.dszerszen.multidrink.data.local.DatabaseDriverFactory
import pl.dszerszen.multidrink.data.network.DrinksApi
import pl.dszerszen.multidrink.data.network.DrinksRepositoryImpl
import pl.dszerszen.multidrink.db.DrinksDatabase
import pl.dszerszen.multidrink.domain.repository.DrinksRepository

class RepositoryModule {

    private val factory by lazy { DatabaseDriverFactory() }

    val drinkRepository: DrinksRepository by lazy {
        DrinksRepositoryImpl(DrinksDatabase(factory.createDbDriver()), DrinksApi())
    }
}