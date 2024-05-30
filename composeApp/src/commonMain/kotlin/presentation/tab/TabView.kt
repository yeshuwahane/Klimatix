package presentation.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator

class TabView {

}


@Composable
fun TabViewSetup(modifier: Modifier = Modifier) {
    TabNavigator(TabA){
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                BottomNavigation(

                ){
                    tabItems(TabA)
                    tabItems(TabB)
                }
            }
        ) {paddingValues ->
            CurrentTab()
        }
    }
}



@Composable
fun RowScope.tabItems(tab: Tab) {

    val navigator = LocalTabNavigator.current
    BottomNavigationItem(
        selected = navigator.current == tab,
        onClick = {
            navigator.current = tab
        },
        icon = {
            tab.options.icon?.let { painter ->
                Icon(painter, contentDescription = tab.options.title)
            }
        },
        modifier = Modifier
            .background(Color.Black, RoundedCornerShape(5.dp)),
        selectedContentColor = Color.Green,
        unselectedContentColor = Color.White
    )
}