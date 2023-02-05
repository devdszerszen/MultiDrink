package pl.dszerszen.multidrink.android.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.dszerszen.multidrink.android.ui.base.InAppEventDispatcher
import pl.dszerszen.multidrink.android.ui.base.NavScreen
import pl.dszerszen.multidrink.android.ui.base.handleException
import pl.dszerszen.multidrink.android.ui.base.navigate
import pl.dszerszen.multidrink.android.ui.search.SearchUiIntent.OnInputChanged
import pl.dszerszen.multidrink.android.ui.search.SearchUiIntent.OnItemClicked
import pl.dszerszen.multidrink.domain.model.Drink
import pl.dszerszen.multidrink.domain.repository.DrinksRepository

class SearchViewModel constructor(
    private val drinksRepository: DrinksRepository,
    private val eventDispatcher: InAppEventDispatcher,
) : ViewModel(), InAppEventDispatcher by eventDispatcher {
    private val _viewState = MutableStateFlow(SearchViewState())
    val viewState = _viewState.asStateFlow()

    private var searchJob: Job? = null

    fun onUiIntent(uiIntent: SearchUiIntent) {
        when (uiIntent) {
            is OnInputChanged -> onSearchInputChanged(uiIntent.text)
            is OnItemClicked -> onItemClicked(uiIntent.drinkId)
        }
    }

    private fun onSearchInputChanged(searchInput: String) {
        searchJob?.cancel()
        if (searchInput.isEmpty()) {
            _viewState.update {
                it.copy(
                    drinks = emptyList(),
                    isInitialState = true,
                    searchInput = searchInput,
                )
            }
        } else {
            search(searchInput)
        }
    }

    private fun onItemClicked(drinkId: String) {
        navigate(NavScreen.Details, drinkId)
    }

    private fun search(text: String) {
        _viewState.update { it.copy(searchInput = text) }
        searchJob = viewModelScope.launch {
            delay(500L)
            _viewState.update { it.copy(isLoading = true, isInitialState = false) }
            val drinks = drinksRepository.findByName(text).handleAndroid { exception ->
                _viewState.update {
                    it.copy(
                        drinks = emptyList(),
                        isLoading = false,
                    )
                }
                handleException(exception)
            }
            drinks?.let {
                _viewState.update {
                    it.copy(
                        drinks = drinks,
                        isLoading = false,
                    )
                }
            }
        }
    }
}

data class SearchViewState(
    val searchInput: String = "",
    val drinks: List<Drink> = emptyList(),
    val isLoading: Boolean = false,
    val isInitialState: Boolean = true,
)

sealed class SearchUiIntent {
    data class OnInputChanged(val text: String) : SearchUiIntent()
    data class OnItemClicked(val drinkId: String) : SearchUiIntent()
}