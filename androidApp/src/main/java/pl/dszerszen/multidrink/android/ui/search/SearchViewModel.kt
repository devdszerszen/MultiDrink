package pl.dszerszen.multidrink.android.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.dszerszen.multidrink.domain.fold
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
            is SearchUiIntent.OnInputChanged -> search(uiIntent.text)
        }
    }

    private fun search(text: String) {
        _viewState.update { it.copy(searchInput = text) }
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(1000L)
            _viewState.update { it.copy(isLoading = true) }
            drinksRepository.findByName(text).let { response ->
                response.fold(
                    onSuccess = { drinks ->
                        _viewState.update {
                            it.copy(
                                drinks = drinks,
                                isLoading = false,
                                errorMessage = null
                            )
                        }
                    },
                    onFail = { error ->
                        _viewState.update {
                            it.copy(
                                drinks = emptyList(),
                                isLoading = false,
                                errorMessage = error.message
                            )
                        }
                    }
                )


            }
        }
    }

}

data class SearchViewState(
    val searchInput: String = "",
    val drinks: List<Drink> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

sealed class SearchUiIntent {
    data class OnInputChanged(val text: String) : SearchUiIntent()
}