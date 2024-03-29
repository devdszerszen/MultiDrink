package pl.dszerszen.multidrink.android.ui.search

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import pl.dszerszen.multidrink.android.AppTheme
import pl.dszerszen.multidrink.android.ui.common.DrinkListItem
import pl.dszerszen.multidrink.android.ui.theme.dimens
import pl.dszerszen.multidrink.domain.model.Drink

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = koinViewModel()
) {
    val state by viewModel.viewState.collectAsState()
    SearchScreen(
        state = state,
        onIntent = viewModel::onUiIntent,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun SearchScreen(
    state: SearchViewState,
    onIntent: (SearchUiIntent) -> Unit,
    modifier: Modifier
) {
    Scaffold(
        modifier = modifier
    ) { paddingValues ->
        Column(Modifier.fillMaxSize()) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimens.large),
                value = state.searchInput,
                onValueChange = { onIntent(SearchUiIntent.OnInputChanged(it)) },
                label = { Text("Input drink name") },
                trailingIcon = {
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp)
                        )
                    }
                },
            )
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                if (state.isInitialState) {
                    item {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Icon(
                                Icons.Default.Face, null,
                                Modifier
                                    .size(32.dp)
                                    .align(CenterHorizontally)
                            )
                            Spacer(Modifier.height(8.dp))
                            Text("Delicious drinks are waiting for you, check it out!")
                        }
                    }
                }
                if (state.drinks.isNotEmpty()) {
                    items(state.drinks, { drink -> drink.id }) {
                        DrinkListItem(it) { id ->
                            onIntent(SearchUiIntent.OnItemClicked(id))
                        }
                    }
                }
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light theme")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark theme")
@Composable
fun SearchScreenPreview_Empty() {
    AppTheme() {
        val state = SearchViewState()
        SearchScreen(state = state, onIntent = {}, modifier = Modifier)
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light theme")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark theme")
@Composable
fun SearchScreenPreview_List() {
    AppTheme() {
        val drinks = List(6) { Drink("id$it", "name$it", "") }
        val state = SearchViewState(drinks = drinks)
        SearchScreen(state = state, onIntent = {}, modifier = Modifier)
    }
}

