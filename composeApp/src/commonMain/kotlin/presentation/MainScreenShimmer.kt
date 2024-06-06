package presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import data.Constant
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import presentation.utils.ColorManager
import presentation.utils.shimmerLoadingAnimation
import presentation.utils.toSp


@Composable
fun WeatherCardShimmer() {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .height(100.dp)
            .fillMaxWidth()
            .shadow(8.dp, RectangleShape)
            .clip(RectangleShape)
            .background(color = ColorManager.Blue, shape = RoundedCornerShape(10.dp))
            .padding(16.dp)
            .shimmerLoadingAnimation()
    )
}


@Composable
fun ForecastItemsShimmer() {
    Column(
        modifier = Modifier
            .size(120.dp, 200.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(width = 1.dp, color = ColorManager.Blue, shape = RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        ComponentRectangleLineShort()
        ComponentCircle()
        ComponentRectangleLineLong()
        ComponentRectangleLineMedium()
    }
}


@Composable
fun ComponentCircle() {
    Box(
        modifier = Modifier
            .background(color = Color.LightGray, shape = CircleShape)
            .size(80.dp)
            .padding(horizontal = 10.dp)
            .shimmerLoadingAnimation(),
    )
}

@Composable
fun ComponentRectangleLineLong() {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = Color.LightGray)
            .size(height = 20.dp, width = 50.dp)
            .shimmerLoadingAnimation()
    )
}

@Composable
fun ComponentRectangleLineShort() {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = Color.LightGray)
            .size(height = 15.dp, width = 35.dp)
            .shimmerLoadingAnimation()
    )
}
@Composable
fun ComponentRectangleLineMedium() {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = Color.LightGray)
            .size(height = 15.dp, width = 35.dp)
            .shimmerLoadingAnimation()
    )
}