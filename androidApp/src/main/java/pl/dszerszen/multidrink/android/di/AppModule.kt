package pl.dszerszen.multidrink.android.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import pl.dszerszen.multidrink.data.local.DatabaseDriverFactory
import pl.dszerszen.multidrink.data.network.DrinksApi
import pl.dszerszen.multidrink.data.network.DrinksRepositoryImpl
import pl.dszerszen.multidrink.db.DrinksDatabase
import pl.dszerszen.multidrink.domain.repository.DrinksRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDrinksDatabase(app: Application): DrinksDatabase {
        return DrinksDatabase(DatabaseDriverFactory(app).createDbDriver())
    }

    @Provides
    @Singleton
    fun provideDrinksApi(): DrinksApi = DrinksApi()

    @Provides
    @Singleton
    fun provideDrinksRepository(database: DrinksDatabase, api: DrinksApi): DrinksRepository {
        return DrinksRepositoryImpl(database, api)
    }
}