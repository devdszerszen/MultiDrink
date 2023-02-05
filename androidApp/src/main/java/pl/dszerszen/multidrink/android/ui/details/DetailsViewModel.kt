package pl.dszerszen.multidrink.android.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pl.dszerszen.multidrink.android.ui.base.InAppEventDispatcher
import pl.dszerszen.multidrink.android.ui.base.NavScreen
import pl.dszerszen.multidrink.android.ui.base.getScreenArg
import pl.dszerszen.multidrink.domain.model.Drink
import pl.dszerszen.multidrink.domain.repository.DrinksRepository

class DetailsViewModel constructor(
    inAppEventDispatcher: InAppEventDispatcher,
    savedStateHandle: SavedStateHandle,
    private val drinksRepository: DrinksRepository
) : ViewModel(), InAppEventDispatcher by inAppEventDispatcher {

    private val _viewState = MutableStateFlow(DetailsViewState())
    val viewState = _viewState.asStateFlow()

    init {
        savedStateHandle.getScreenArg(NavScreen.Details, onSuccess = ::fetchDrinkDetails, onError = {
            //TODO error handling
        })
    }

    private fun fetchDrinkDetails(drinkId: String) {
        viewModelScope.launch {
            val drink = drinksRepository.findById(drinkId).handleAndroid {
                //TODO error handling
            }
            _viewState.update { it.copy(drink = drink) }
        }
    }
}

data class DetailsViewState(
    val drink: Drink? = null
)