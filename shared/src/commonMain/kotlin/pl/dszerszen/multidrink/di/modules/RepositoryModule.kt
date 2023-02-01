package pl.dszerszen.multidrink.di.modules

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.dsl.module
import pl.dszerszen.multidrink.data.network.DrinksRepositoryImpl
import pl.dszerszen.multidrink.domain.repository.DrinksRepository

val repositoryModule = module {
    single<DrinksRepository> { DrinksRepositoryImpl(get(), get()) }
}

class RepositoryModuleDI : KoinComponent {
    val drinksRepository: DrinksRepository by inject()
}