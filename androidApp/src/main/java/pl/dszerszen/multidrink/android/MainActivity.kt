package pl.dszerszen.multidrink.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.android.ext.android.inject
import pl.dszerszen.multidrink.android.ui.base.InAppEvent
import pl.dszerszen.multidrink.android.ui.base.InAppEvent.Navigate
import pl.dszerszen.multidrink.android.ui.base.InAppEvent.NavigateBack
import pl.dszerszen.multidrink.android.ui.base.InAppEventHandler
import pl.dszerszen.multidrink.android.ui.base.NavScreen
import pl.dszerszen.multidrink.android.ui.details.DetailsScreen
import pl.dszerszen.multidrink.android.ui.search.SearchScreen

class MainActivity : ComponentActivity() {

    private val inAppEventHandler: InAppEventHandler by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            MyApplicationTheme {
                LaunchedEffect(Unit) {
                    inAppEventHandler.handleEvent { event ->
                        when (event) {
                            is Navigate -> navController.navigate(event.target.createRoute(event.argument))
                            NavigateBack -> navController.popBackStack()
                            is InAppEvent.Error -> {
                                //TODO handle error
                            }
                        }
                    }
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavRoot(navController)
                }
            }
        }
    }
}

@Composable
private fun NavRoot(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavScreen.Search.baseRoute()
    ) {
        composable(NavScreen.Search.baseRoute()) {
            SearchScreen()
        }
        composable(NavScreen.Details.baseRoute()) {
            DetailsScreen()
        }
    }
}
