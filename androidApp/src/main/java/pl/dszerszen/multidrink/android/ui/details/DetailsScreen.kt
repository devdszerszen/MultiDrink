package pl.dszerszen.multidrink.android.ui.details

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel
import pl.dszerszen.multidrink.android.MyApplicationTheme
import pl.dszerszen.multidrink.domain.model.Drink

@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel = koinViewModel()
) {
    val state by viewModel.viewState.collectAsState()
    DetailsScreen(
        drink = state.drink,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun DetailsScreen(
    drink: Drink?,
    modifier: Modifier = Modifier
) {
    if (drink != null) {
        DrinkDetails(drink)
    } else {
        //TODO add empty state
    }
}

@Composable
private fun DrinkDetails(drink: Drink) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = drink.image,
            contentDescription = null,
            modifier = Modifier.clip(CircleShape)
        )
        Spacer(Modifier.height(16.dp))
        Text(drink.name)
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light theme")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark theme")
@Composable
fun DetailsScreenPreview() {
    MyApplicationTheme {
        val drink = Drink("id", "name", null)
        DetailsScreen(drink)
    }
}