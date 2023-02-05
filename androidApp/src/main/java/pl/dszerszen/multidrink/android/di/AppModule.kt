package pl.dszerszen.multidrink.android.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import pl.dszerszen.multidrink.android.ui.base.InAppComponent
import pl.dszerszen.multidrink.android.ui.base.InAppEventDispatcher
import pl.dszerszen.multidrink.android.ui.base.InAppEventHandler
import pl.dszerszen.multidrink.android.ui.details.DetailsViewModel
import pl.dszerszen.multidrink.android.ui.search.SearchViewModel

val appModule = module {
    //region view models ----------------
    viewModelOf(::SearchViewModel)
    viewModelOf(::DetailsViewModel)
    //endregion view models -------------
    single<InAppEventDispatcher> { InAppComponent }
    single<InAppEventHandler> { InAppComponent }
}