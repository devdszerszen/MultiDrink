package pl.dszerszen.multidrink.android.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import pl.dszerszen.multidrink.android.ui.search.SearchViewModel

val appModule = module {
    viewModel { SearchViewModel(get()) }
}