package pl.dszerszen.multidrink.android.ui.base

import androidx.lifecycle.SavedStateHandle
import pl.dszerszen.multidrink.domain.AppException

enum class NavScreen(private val route: String, val argumentName: String? = null) {
    Search("search_screen"),
    Details("details_screen", "id");

    fun baseRoute(): String {
        return buildString {
            append(route)
            argumentName?.let { append("/{${it}}") }
        }
    }

    fun createRoute(argumentValue: String?): String {
        return buildString {
            append(route)
            if (argumentValue != null && argumentName != null) {
                append("/$argumentValue")
            }
        }
    }
}

fun SavedStateHandle.getScreenArg(
    screen: NavScreen,
    onSuccess: (String) -> Unit,
    onError: (AppException) -> Unit
) {
    val id = screen.argumentName
    if (id == null) {
        onError(AppException.BusinessException("Cannot get argument name for screen: ${screen.name}"))
    } else {
        get<String>(id)?.let {
            onSuccess(it)
        } ?: onError(AppException.BusinessException("Cannot get argument from saved state"))
    }
}