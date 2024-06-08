package presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.InfiniteRepeatableSpec
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import data.Constant
import data.model.forecast.CityForecastModel
import data.utils.DataResource
import io.github.aakira.napier.Napier
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import presentation.utils.ColorManager
import presentation.utils.toDp
import presentation.utils.toSp

class MainScreen : Screen {
    @Composable
    override fun Content() {
        val mainViewModel = getScreenModel<MainViewModel>()
        val uiState by mainViewModel.uiState.collectAsState()
        val forecastUiState by mainViewModel.forecastUiState.collectAsState()
        val currentHour = mainViewModel.getLocalTime()

//        val man by mainViewModel.uiState.collectAsState()

        val snackBar = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()


        val sunIcon =
            "https://raw.githubusercontent.com/yeshuwahane/weatherImages/main/weather_images/star.png"
        val windIcon =
            "https://raw.githubusercontent.com/yeshuwahane/weatherImages/main/weather_images/wind.png"
        val humidityIcon =
            "https://raw.githubusercontent.com/yeshuwahane/weatherImages/main/weather_images/drop.png"
        val airIcon =
            "https://raw.githubusercontent.com/yeshuwahane/weatherImages/main/weather_images/breath.png"
        val pressureIcon =
            "https://raw.githubusercontent.com/yeshuwahane/weatherImages/main/weather_images/arrows.png"
        val visibilityIcon =
            "https://raw.githubusercontent.com/yeshuwahane/weatherImages/main/weather_images/visibility.png"



        LaunchedEffect(Unit) {
            mainViewModel.getForecast()

        }


        Scaffold(
            backgroundColor = Color.Transparent,   // Make the background transparent,
            snackbarHost = {
                SnackbarHost(snackBar)
            }

        ) { paddingValues ->

            WeatherStaticCompose(
                paddingValues = paddingValues,
                onSearchCTA = {
                    mainViewModel.setCity(it)
                },
                uvIndex = uiState.uvIndex,
                sunIcon = sunIcon,
                windIcon = windIcon,
                windSpeed = uiState.windKph,
                humidityIcon = humidityIcon,
                humidityPercent = uiState.humidity,
                airIcon = airIcon,
                airIndex = uiState.airQualityIndex,
                pressureIcon = pressureIcon,
                pressureMb = uiState.pressure_mb,
                visibilityIcon = visibilityIcon,
                visibilityKm = uiState.visibilityKm,
                locationName = uiState.cityName,
                dayStatus = mainViewModel.getTimeOfDay(currentHour),
                forecastDataResource = forecastUiState.weatherForecastResource,
                onSnackBarCTA = {
                    scope.launch {
                        snackBar.showSnackbar(it)
                    }
                },
                onRefreshCTA = {
                    mainViewModel.getForecast()
                },
                currentHour = currentHour
            )

        }


    }

    @OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
    @Composable
    fun WeatherStaticCompose(
        paddingValues: PaddingValues,
        onSearchCTA: (String) -> Unit,
        locationName: String,
        uvIndex: Double,
        sunIcon: String,
        windIcon: String,
        windSpeed: String,
        humidityIcon: String,
        humidityPercent: String,
        airIndex: Int,
        airIcon: String,
        pressureIcon: String,
        pressureMb: Double,
        visibilityIcon: String,
        visibilityKm: Double,
        dayStatus: String,
        currentHour: Int,
        forecastDataResource: DataResource<CityForecastModel>,
        onSnackBarCTA: (String) -> Unit,
        onRefreshCTA: () -> Unit,
    ) {
        var cityName by remember { mutableStateOf(TextFieldValue("")) }
        val focusManager = LocalFocusManager.current
        val hapticFeedback = LocalHapticFeedback.current
        var showTextFeild by remember { mutableStateOf(false) }

        val refreshing by remember { mutableStateOf(false) }

        val refreshState = rememberPullRefreshState(refreshing, onRefresh = {
            Napier.d("Pull to refresh() ")
            onRefreshCTA.invoke()
        })

        val uvItemData = WeatherDetailCardItemData(sunIcon, getUVHarmfulness(uvIndex), "UV Index")
        val windItemData = WeatherDetailCardItemData(windIcon, windSpeed + "km/h", "Wind")
        val humidityItemData =
            WeatherDetailCardItemData(humidityIcon, "$humidityPercent%", "Humidity")
        val airIndexItemData =
            WeatherDetailCardItemData(airIcon, getAirQuality(airIndex), "Air Index")
        val pressureIndexItemData =
            WeatherDetailCardItemData(pressureIcon, "$pressureMb MB", "Pressure")
        val visibilityIndexItemData =
            WeatherDetailCardItemData(visibilityIcon, "$visibilityKm KM", "Visibility")

        val infiniteTransition = rememberInfiniteTransition()
        val vibration by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 10f,
            animationSpec = InfiniteRepeatableSpec(
                animation = tween(500),
                repeatMode = RepeatMode.Reverse
            )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .systemBarsPadding()
                .pullRefresh(refreshState)
                .navigationBarsPadding(),
        ) {

            Row(
                modifier = Modifier.fillMaxWidth().padding(10.dp).pullRefresh(refreshState),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Good $dayStatus",
                    modifier = Modifier.padding(10.dp),
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.dp.toSp()
                )
                Row(
                    modifier = Modifier.border(2.dp, ColorManager.Blue, RoundedCornerShape(8.dp))
                        .padding(10.dp).clickable {
                            showTextFeild = !showTextFeild
                        }
                ) {
                    Icon(Icons.Default.LocationOn, contentDescription = "")
                    Text(locationName, color = ColorManager.Blue)
                }
            }

            AnimatedVisibility(showTextFeild) {
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

            if (forecastDataResource.isSuccess() && forecastDataResource.data?.current?.condition?.text?.isNotEmpty() == true) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .shadow(8.dp, RectangleShape)
                        .clip(RectangleShape)
                        .background(color = ColorManager.Blue, shape = RoundedCornerShape(10.dp))
                        .padding(16.dp)
                        .combinedClickable(enabled = true, onLongClick = {
                            onRefreshCTA.invoke()
                            hapticFeedback.performHapticFeedback(hapticFeedbackType = HapticFeedbackType.LongPress)
//                            onSnackBarCTA.invoke("Refreshing")
                        },
                            onLongClickLabel = "Long Click to Refresh",
                        ){
                            onSnackBarCTA.invoke("Long press to Refresh")
                            hapticFeedback.performHapticFeedback(hapticFeedbackType = HapticFeedbackType.LongPress)

                        }
                ) {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 10.dp, vertical = 10.dp)
                    ) {
                        Text(
                            text = forecastDataResource.data.current.tempC.toString() + "°",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.dp.toSp()
                        )
                        Text(
                            text = forecastDataResource.data.current.condition.text,
                            maxLines = 2,
                            color = ColorManager.WhiteBlack,
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.dp.toSp()
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    KamelImage(
                        asyncPainterResource(Constant.IMG_BASE_URL + forecastDataResource.data.current.condition.icon),
                        "forecast icon",
                        modifier = Modifier
                            .size(80.dp)
                            .padding(horizontal = 10.dp)
                            .graphicsLayer {
                                translationX = vibration
                            },
                        contentScale = ContentScale.Crop
                    )

                }
            } else if (forecastDataResource.isLoading()) {
                WeatherCardShimmer()
            } else if (forecastDataResource.isError()) {
                onSnackBarCTA.invoke(forecastDataResource.error?.message.toString())
            }

            Spacer(modifier = Modifier.weight(1f))


            if (forecastDataResource.isLoading())
            {
                LazyRow(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(10) {
                        ForecastItemsShimmer()
                    }

                }
            } else if (forecastDataResource.isSuccess() && forecastDataResource.data?.forecast?.forecastday?.isNotEmpty() == true) {

                LazyRow(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(forecastDataResource.data.forecast.forecastday.get(0).hour) { hourForecast ->

                        WeatherCard(
                            time = hourForecast.time.split(" ")[1],
                            weatherIcon = hourForecast.condition.icon,
                            temp = hourForecast.tempC.toString(),
                            weatherCondition = hourForecast.condition.text,
                        )

                    }

                }

            }


            Spacer(modifier = Modifier.weight(1f))

            WeatherDetailCard(
                weatherDetailItemDataList1 = listOf(uvItemData, windItemData, humidityItemData),
                weatherDetailItemDataList2 = listOf(
                    airIndexItemData,
                    pressureIndexItemData,
                    visibilityIndexItemData
                )
            )

        }
    }

    @Composable
    fun WeatherCard(time: String, weatherIcon: String, temp: String, weatherCondition: String) {
        val infiniteTransition = rememberInfiniteTransition()
        val vibration by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 10f,
            animationSpec = InfiniteRepeatableSpec(
                animation = tween(1000),
                repeatMode = RepeatMode.Reverse
            )
        )
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
            Text(time, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            KamelImage(
                asyncPainterResource(Constant.IMG_BASE_URL + weatherIcon),
                "",
                modifier = Modifier
                    .size(80.dp)
                    .graphicsLayer {
                        translationX = vibration
                    }
                    .padding(horizontal = 10.dp),
                contentScale = ContentScale.Crop
            )
            Text("$temp°", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Text(weatherCondition, fontSize = 14.sp, color = Color.Gray)
        }
    }

    @Composable
    fun WeatherDetailCard(
        weatherDetailItemDataList1: List<WeatherDetailCardItemData>,
        weatherDetailItemDataList2: List<WeatherDetailCardItemData>
    ) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(10.dp).wrapContentHeight(), elevation = 10.dp
        ) {
            Column {
                LazyRow(
                    modifier = Modifier.padding(10.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,

                    ) {
                    items(weatherDetailItemDataList1) { itemData ->
                        WeatherDetailCardItem(itemData)
                    }
                }
                LazyRow(
                    modifier = Modifier.padding(10.dp).fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,

                    ) {
                    items(weatherDetailItemDataList2) { itemData ->
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
                modifier = Modifier.size(90.toDp()),
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



