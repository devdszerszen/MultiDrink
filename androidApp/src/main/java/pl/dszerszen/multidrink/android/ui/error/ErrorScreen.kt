package pl.dszerszen.multidrink.android.ui.error

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.ThumbUp
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.dszerszen.multidrink.android.MyApplicationTheme
import pl.dszerszen.multidrink.android.ui.error.BottomInfoMessageType.*

class BottomInfoState(
    val title: String = "",
    val desc: String? = null,
    val type: BottomInfoMessageType = INFO
) {
    fun isInitialState() = title.isEmpty() && desc.isNullOrEmpty() && type == INFO
}

@Composable
fun ColumnScope.BottomInfoContent(onClose: () -> Unit, state: BottomInfoState) {
    IconButton(
        modifier = Modifier
            .padding(8.dp)
            .align(Alignment.End),
        onClick = onClose
    ) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = null,
        )
    }
    Spacer(Modifier.height(8.dp))
    Box(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 48.dp)
    ) {
        if (state.isInitialState().not()) {
            BottomInfoMessage(
                state = state,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun BottomInfoMessage(
    state: BottomInfoState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = CenterHorizontally
    ) {
        Image(
            imageVector = when (state.type) {
                ERROR -> Icons.Rounded.Warning
                INFO -> Icons.Rounded.Info
                SUCCESS -> Icons.Rounded.ThumbUp
            },
            contentDescription = null,
            colorFilter = ColorFilter.tint(state.type.color),
            modifier = Modifier.size(128.dp)
        )
        Spacer(Modifier.height(8.dp))
        Text(state.title)
        state.desc?.let {
            Spacer(Modifier.height(8.dp))
            Text(it)
        }
    }
}

enum class BottomInfoMessageType(val color: Color) {
    ERROR(Color.Red),
    INFO(Color.Yellow),
    SUCCESS(Color.Green)
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light theme", showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Dark theme", showBackground = true)
@Composable
fun ErrorScreenPreview() {
    @Composable
    fun PreviewBox(content: @Composable BoxScope.() -> Unit) {
        Box(
            Modifier
                .border(1.dp, Color.Black)
                .padding(8.dp)
        ) {
            content()
        }
    }

    MyApplicationTheme() {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = CenterHorizontally
        ) {
            PreviewBox {
                BottomInfoMessage(BottomInfoState("success title", "success description", SUCCESS))
            }
            PreviewBox {
                BottomInfoMessage(BottomInfoState("info title", "info description", INFO))
            }
            PreviewBox {
                BottomInfoMessage(BottomInfoState("error title", "error description", ERROR))
            }
        }
    }
}