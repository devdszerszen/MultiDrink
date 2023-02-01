package pl.dszerszen.multidrink.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.dszerszen.multidrink.android.ui.search.SearchScreen
import pl.dszerszen.multidrink.android.ui.search.SearchViewModel

class MainActivity : ComponentActivity() {

    private val searchViewModel: SearchViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    SearchScreen(
                        viewModel = searchViewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}
