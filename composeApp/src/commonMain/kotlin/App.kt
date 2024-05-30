import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import di.initKoin
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.MainScreen

@OptIn(ExperimentalResourceApi::class)
@Composable
@Preview
fun App() {
    initKoin {

    }
    MaterialTheme {
        Navigator(MainScreen())
    }
}

