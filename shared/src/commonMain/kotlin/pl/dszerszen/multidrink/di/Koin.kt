package pl.dszerszen.multidrink.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import pl.dszerszen.multidrink.data.local.databaseModule
import pl.dszerszen.multidrink.di.modules.commonModule
import pl.dszerszen.multidrink.di.modules.repositoryModule

fun initKoin(appDeclaration: KoinAppDeclaration = {}) {
    startKoin {
        appDeclaration()
        modules(
            databaseModule(),
            commonModule,
            repositoryModule
        )
    }
}

//iOS
fun initKoin() = initKoin { }