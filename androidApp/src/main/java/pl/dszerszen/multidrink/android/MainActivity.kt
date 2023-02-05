package pl.dszerszen.multidrink.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import pl.dszerszen.multidrink.android.ui.base.InAppEvent
import pl.dszerszen.multidrink.android.ui.base.InAppEvent.Navigate
import pl.dszerszen.multidrink.android.ui.base.InAppEvent.NavigateBack
import pl.dszerszen.multidrink.android.ui.base.InAppEventHandler
import pl.dszerszen.multidrink.android.ui.base.NavScreen
import pl.dszerszen.multidrink.android.ui.details.DetailsScreen
import pl.dszerszen.multidrink.android.ui.error.BottomInfoContent
import pl.dszerszen.multidrink.android.ui.error.BottomInfoState
import pl.dszerszen.multidrink.android.ui.search.SearchScreen

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
class MainActivity : ComponentActivity() {

    private val inAppEventHandler: InAppEventHandler by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val coroutineScope = rememberCoroutineScope()

            val navController = rememberNavController()
            val keyboardController = LocalSoftwareKeyboardController.current

            val modalBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
            var bottomInfoState by remember { mutableStateOf(BottomInfoState()) }

            MyApplicationTheme {
                LaunchedEffect(Unit) {
                    inAppEventHandler.handleEvent { event ->
                        when (event) {
                            is Navigate -> navController.navigate(
                                route = event.target.createRoute(event.argument)
                            )
                            NavigateBack -> navController.popBackStack()
                            is InAppEvent.BottomInfo -> {
                                bottomInfoState = BottomInfoState(
                                    title = event.title,
                                    desc = event.desc,
                                    type = event.type
                                )
                            }
                        }
                    }
                }

                LaunchedEffect(bottomInfoState) {
                    if (bottomInfoState.isInitialState().not()) {
                        keyboardController?.hide()
                        modalBottomSheetState.show()
                    }
                }

                BackHandler(enabled = modalBottomSheetState.isVisible) {
                    coroutineScope.launch { modalBottomSheetState.hide() }
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ModalBottomSheetLayout(
                        sheetState = modalBottomSheetState,
                        sheetContent = {
                            BottomInfoContent(
                                onClose = { coroutineScope.launch { modalBottomSheetState.hide() } },
                                state = bottomInfoState
                            )
                        }
                    ) {
                        NavRoot(navController)
                    }
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