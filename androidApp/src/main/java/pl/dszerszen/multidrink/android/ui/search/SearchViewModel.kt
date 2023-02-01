package pl.dszerszen.multidrink.android.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.dszerszen.multidrink.domain.model.Drink
import pl.dszerszen.multidrink.domain.repository.DrinksRepository

class SearchViewModel constructor(
    private val drinksRepository: DrinksRepository
) : ViewModel() {
    private val _viewState = MutableStateFlow(SearchViewState())
    val viewState = _viewState.asStateFlow()

    private var searchJob: Job? = null

    fun onUiIntent(uiIntent: SearchUiIntent) {
        when (uiIntent) {
            is SearchUiIntent.OnInputChanged -> onSearchInputChanged(uiIntent.text)
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
                    errorMessage = null
                )
            }
        } else {
            search(searchInput)
        }
    }

    private fun search(text: String) {
        _viewState.update { it.copy(searchInput = text, errorMessage = null) }
        searchJob = viewModelScope.launch {
            delay(500L)
            _viewState.update { it.copy(isLoading = true, isInitialState = false) }
            val drinks = drinksRepository.findByName(text).handleAndroid { exception ->
                _viewState.update {
                    it.copy(
                        drinks = emptyList(),
                        isLoading = false,
                        errorMessage = exception.message
                    )
                }
            }
            drinks?.let {
                _viewState.update {
                    it.copy(
                        drinks = drinks,
                        isLoading = false,
                        errorMessage = null
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
    val errorMessage: String? = null
)

sealed class SearchUiIntent {
    data class OnInputChanged(val text: String) : SearchUiIntent()
}