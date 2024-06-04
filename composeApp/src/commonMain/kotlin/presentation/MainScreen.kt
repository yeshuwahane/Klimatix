package presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import data.Constant
import io.github.aakira.napier.Napier
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.utils.ColorManager
import presentation.utils.toDp
import presentation.utils.toSp
import weather.composeapp.generated.resources.Res
import weather.composeapp.generated.resources.clouds
import weather.composeapp.generated.resources.rain
import weather.composeapp.generated.resources.sunny

class MainScreen : Screen {
    @Composable
    override fun Content() {
        val mainViewModel = getScreenModel<MainViewModel>()
        val uiState = mainViewModel.uiState.collectAsState()
        val temp = uiState.value.temp
        val weatherName = uiState.value.weatherCondition
        val locationName = uiState.value.cityName
        val weatherIcon = uiState.value.iconCode

        val sunIcon ="https://raw.githubusercontent.com/yeshuwahane/weatherImages/main/cdn.weatherapi.com/weather/64x64/day/113.png"
        val windIcon = "https://raw.githubusercontent.com/yeshuwahane/weatherImages/main/weather_images/air.png"
        val humidityIcon = "https://raw.githubusercontent.com/yeshuwahane/weatherImages/main/weather_images/drop.png"
        val airIcon = "https://raw.githubusercontent.com/yeshuwahane/weatherImages/main/weather_images/breath.png"
        val pressureIcon = "https://raw.githubusercontent.com/yeshuwahane/weatherImages/main/weather_images/arrows.png"
        val visibilityIcon = "https://raw.githubusercontent.com/yeshuwahane/weatherImages/main/weather_images/visibility.png"



        LaunchedEffect(Unit) {
            mainViewModel.getWeather()
        }

        Box {
            Scaffold(
                backgroundColor = Color.Transparent,   // Make the background transparent

            ) { paddingValues ->

                WeatherStaticCompose(
                    paddingValues = paddingValues,
                    onSearchCTA = {
                        mainViewModel.setCity(it)
                    },
                    temp = temp,
                    weatherIcon = weatherIcon,
                    weatherName = weatherName,
                    tempFeelLike = uiState.value.feelslikeTemp,
                    uvIndex = uiState.value.uvIndex,
                    sunIcon = sunIcon,
                    windIcon = windIcon,
                    windSpeed = uiState.value.windKph,
                    humidityIcon = humidityIcon,
                    humidityPercent = uiState.value.humidity,
                    airIcon = airIcon,
                    airIndex = uiState.value.airQualityIndex,
                    pressureIcon = pressureIcon,
                    pressureMb = uiState.value.pressure_mb,
                    visibilityIcon = visibilityIcon,
                    visibilityKm = uiState.value.visibilityKm,
                    locationName = uiState.value.cityName,
                    dayStatus = uiState.value.dayStatus
                )

            }
        }


    }

    @Composable
    fun WeatherStaticCompose(
        paddingValues: PaddingValues,
        onSearchCTA: (String) -> Unit,
        locationName:String,
        temp: String,
        weatherIcon: String,
        weatherName: String,
        tempFeelLike: String,
        uvIndex: Double,
        sunIcon:String,
        windIcon:String,
        windSpeed:String,
        humidityIcon:String,
        humidityPercent:String,
        airIndex:Int,
        airIcon:String,
        pressureIcon:String,
        pressureMb:Double,
        visibilityIcon:String,
        visibilityKm:Double,
        dayStatus:String
    ) {
        var cityName by remember { mutableStateOf(TextFieldValue("")) }
        val focusManager = LocalFocusManager.current
        var showTextFeild by remember { mutableStateOf(false) }

        val uvItemData =WeatherDetailCardItemData(sunIcon, getUVHarmfulness(uvIndex), "UV Index")
        val windItemData =WeatherDetailCardItemData(windIcon, windSpeed+"km/h", "Wind")
        val humidityItemData =WeatherDetailCardItemData(humidityIcon, "$humidityPercent%", "Humidity")
        val airIndexItemData =WeatherDetailCardItemData(airIcon, getAirQuality(airIndex), "Air Index")
        val pressureIndexItemData =WeatherDetailCardItemData(pressureIcon, "$pressureMb MB", "Pressure")
        val visibilityIndexItemData =WeatherDetailCardItemData(visibilityIcon, "$visibilityKm KM", "Visibility")



        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
        ) {

            Row(modifier = Modifier.fillMaxWidth().padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween){

                Text("Good $dayStatus",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.dp.toSp())
                Row(
                    modifier = Modifier.border(2.dp,ColorManager.Blue, RoundedCornerShape(8.dp)).padding(10.dp).clickable {
                        showTextFeild = !showTextFeild
                    }
                ) {
                    Icon(Icons.Default.LocationOn, contentDescription = "")
                    Text(locationName, color = ColorManager.Blue)
                }
            }

            AnimatedVisibility(showTextFeild){
                OutlinedTextField(
                value = cityName,
                label = { Text(text = "Search City") },
                onValueChange = {
                    cityName = it
                },
                leadingIcon = {
                    Icon(Icons.Default.Search, "")
                },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 10.dp),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    autoCorrect = true,
                    imeAction = ImeAction.Go,
                    keyboardType = KeyboardType.Text
                ),
                keyboardActions = KeyboardActions(
                    onGo = {
                        onSearchCTA.invoke(cityName.text)
                        focusManager.clearFocus()
                        showTextFeild = false
                    }
                )
            )
            }



            Box(
                contentAlignment = Alignment.TopCenter, modifier = Modifier
                    .fillMaxWidth()
//                    .wrapContentHeight()
            ) {
                Spacer(modifier = Modifier.height(10.dp))

                Card(
                    modifier = Modifier.fillMaxWidth().padding(10.dp),
                    shape = MaterialTheme.shapes.large
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = temp,
                                fontWeight = FontWeight.Bold,
                                fontSize = 26.dp.toSp()
                            )

                            KamelImage(
                                asyncPainterResource(Constant.IMG_BASE_URL + weatherIcon),
                                "",
                                modifier = Modifier.size(74.dp),
                                contentScale = ContentScale.Crop
                            )

                            Text(
                                text = weatherName,
                                fontWeight = FontWeight.Bold,
                                fontSize = 26.dp.toSp()
                            )

                        }
//                            Text(text = "Feel Like $tempFeelLike /")
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            WeatherDetailCard(
                weatherDetailItemDataList1 = listOf(uvItemData,windItemData,humidityItemData),
                weatherDetailItemDataList2 = listOf(airIndexItemData,pressureIndexItemData,visibilityIndexItemData)
            )

        }
    }

    @Composable
    fun WeatherDetailCard(weatherDetailItemDataList1: List<WeatherDetailCardItemData>,weatherDetailItemDataList2: List<WeatherDetailCardItemData>) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(10.dp).wrapContentHeight(), elevation = 10.dp
        ) {
            Column {
                LazyRow(
                    modifier = Modifier.padding(10.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,

                ) {
                    items(weatherDetailItemDataList1){itemData ->
                        WeatherDetailCardItem(itemData)
                    }
                }
                LazyRow(
                    modifier = Modifier.padding(10.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,

                    ) {
                    items(weatherDetailItemDataList2){itemData ->
                        WeatherDetailCardItem(itemData)
                    }
                }
            }

        }
    }

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    fun WeatherDetailCardItem(weatherItemData: WeatherDetailCardItemData) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(5.dp).wrapContentSize()
        ) {
            KamelImage(
                asyncPainterResource(weatherItemData.weatherIcon),
                "",
                modifier = Modifier.size(150.toDp()),
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(ColorManager.Blue)
            )

            Text(
                text = weatherItemData.conditionDes,
                fontWeight = FontWeight.Bold,
                fontSize = 14.dp.toSp()
            )
            Text(
                text = weatherItemData.conditionName,
                fontSize = 11.dp.toSp()
            )


        }
    }


}


fun getUVHarmfulness(uvIndex: Double): String {
    return when {
        uvIndex < 0 -> "Invalid UV index"
        uvIndex == 0.0 -> "No UV radiation"
        uvIndex in 0.1..2.9 -> "Low"
        uvIndex in 3.0..5.9 -> "Moderate"
        uvIndex in 6.0..7.9 -> "Risk"
        uvIndex in 8.0..10.9 -> "High Risk"
        uvIndex >= 11.0 -> "Extreme Risk"
        else -> "Unknown risk level"
    }
}

fun getAirQuality(index: Int): String = when (index) {
    1 -> "Good"
    2 -> "Moderate"
    3 -> "Unhealthy for sensitive groups"
    4 -> "Unhealthy"
    5 -> "Very Unhealthy"
    6 -> "Hazardous"
    else -> "Invalid index"
}

data class WeatherDetailCardItemData(
    val weatherIcon: String, val conditionDes: String, val conditionName: String
)


