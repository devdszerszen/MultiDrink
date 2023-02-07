package pl.dszerszen.multidrink.android.ui.common

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import pl.dszerszen.multidrink.android.AppTheme
import pl.dszerszen.multidrink.android.R
import pl.dszerszen.multidrink.android.ui.theme.dimens
import pl.dszerszen.multidrink.domain.model.Drink

@Composable
fun DrinkListItem(
    drink: Drink,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit
) {
    Card(
        modifier = modifier.clickable { onClick(drink.id) },
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier
                    .padding(all = 8.dp)
                    .align(CenterVertically),
                text = drink.name,
                style = typography.h5
            )
            Spacer(Modifier.weight(1f))
            AsyncImage(
                model = drink.image,
                contentDescription = null,
                modifier = Modifier
                    .padding(dimens.medium)
                    .clip(CircleShape)
                    .size(64.dp)
                    .background(colors.background),
                placeholder = painterResource(R.drawable.cocktail)
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light theme")
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark theme")
@Composable
fun DrinkListItemPreview() {
    AppTheme() {
        val drink = Drink("id", "name", "img")
        DrinkListItem(drink) {}
    }
}