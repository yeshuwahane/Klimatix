package presentation.tab

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

object TabB :Tab{
    @Composable
    override fun Content() {
        TODO("Not yet implemented")
    }

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.Home)
            val title = "Tab 2"
            val index: UShort = 1u
            return TabOptions(index, title, icon)
        }
}