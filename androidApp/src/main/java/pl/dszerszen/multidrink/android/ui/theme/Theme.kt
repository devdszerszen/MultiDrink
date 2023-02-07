package pl.dszerszen.multidrink.android

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pl.dszerszen.multidrink.android.ui.theme.Dimens
import pl.dszerszen.multidrink.android.ui.theme.LocalDimens

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColors(
            primary = Color(0xFF80D8FF),
            primaryVariant = Color(0xFF82B1FF),
            secondary = Color(0xFFEA80FC),
            background = Color.Black,
            onBackground = Color.White,
            surface = Color.DarkGray,
            onSurface = Color.White
        )
    } else {
        lightColors(
            primary = Color(0xFFFF6D00),
            primaryVariant = Color(0xFFFFAB00),
            secondary = Color(0xFFDD2C00),
            background = Color.White,
            onBackground = Color.Black,
            surface = Color.LightGray,
            onSurface = Color.Black
        )
    }
    val typography = Typography(
        body1 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        )
    )
    val shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(4.dp),
        large = RoundedCornerShape(0.dp)
    )

    CompositionLocalProvider(
        LocalDimens provides Dimens()
    ) {
        MaterialTheme(
            colors = colors,
            typography = typography,
            shapes = shapes,
            content = content
        )
    }
}

fun Color.semiTransparent() = this.copy(alpha = 0.5f)