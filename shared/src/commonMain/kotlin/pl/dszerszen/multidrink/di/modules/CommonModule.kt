package pl.dszerszen.multidrink.di.modules

import org.koin.dsl.module
import pl.dszerszen.multidrink.data.network.DrinksApi

val commonModule = module {
    single { DrinksApi() }
}