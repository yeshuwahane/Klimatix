package presentation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import io.github.aakira.napier.Napier
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import weather.composeapp.generated.resources.Res
import weather.composeapp.generated.resources.clouds
import weather.composeapp.generated.resources.rain

class MainScreen : Screen {
    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        val mainViewModel = getScreenModel<MainViewModel>()
        val uiState = mainViewModel.uiState.collectAsState()
        val temp = uiState.value.temp
        val weatherName = uiState.value.weatherCondition
        val locationName = uiState.value.cityName

        var cityName by remember { mutableStateOf(TextFieldValue("")) }
        var showContent by remember { mutableStateOf(false) }
        val focusManager = LocalFocusManager.current



        Box {
            Scaffold(
                backgroundColor = Color.Transparent,   // Make the background transparent

            ) { paddingValues ->
                Column(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    verticalArrangement = Arrangement.Top
                ) {

                    OutlinedTextField(
                        value = cityName,
                        label = { Text(text = "Search City") },
                        onValueChange = {
                            cityName = it
                        },
                        leadingIcon = {
                            Icon(Icons.Default.Search, "")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(
                            autoCorrect = true,
                            imeAction = ImeAction.Go,
                            keyboardType = KeyboardType.Text
                        ),
                        keyboardActions = KeyboardActions(
                            onGo = {
                                mainViewModel.setCity(cityName.text)
                                focusManager.clearFocus()
                            }
                        )
                    )

                    Box(
                        contentAlignment = Alignment.Center, modifier = Modifier
                            .fillMaxSize()
                    ) {

                        if (weatherName.contains("clouds", ignoreCase = true)) {
                            weatherImage(
                                cityName = locationName,
                                painter = painterResource(resource = Res.drawable.clouds),
                                imgUrl = "https://raw.githubusercontent.com/yeshuwahane/weatherImages/main/weather_images/dark%20clouds.jpeg",
                                weatherName = weatherName,
                                temperature = temp,
                            )
                            Napier.d("clouds")
                        } else if (weatherName.contains("Rain", ignoreCase = true)) {
                            weatherImage(
                                cityName = locationName,
                                painter = painterResource(resource = Res.drawable.rain),
                                imgUrl = "https://raw.githubusercontent.com/yeshuwahane/weatherImages/main/weather_images/rain.jpeg",
                                weatherName = weatherName,
                                temperature = temp,
                            )
                            Napier.d("Rain")
                        } else if (weatherName.contains("clear sky", ignoreCase = true)) {
                            weatherImage(
                                cityName = locationName,
                                painter = painterResource(resource = Res.drawable.rain),
                                imgUrl = "https://raw.githubusercontent.com/yeshuwahane/weatherImages/main/weather_images/clear%20sky.jpeg",
                                weatherName = weatherName,
                                temperature = temp,
                            )
                            Napier.d("clear sky")
                        } else if (weatherName.contains("mist", ignoreCase = true)) {
                            weatherImage(
                                cityName = locationName,
                                painter = painterResource(resource = Res.drawable.rain),
                                imgUrl = "https://raw.githubusercontent.com/yeshuwahane/weatherImages/main/weather_images/mist.jpeg",
                                weatherName = weatherName,
                                temperature = temp,
                            )
                            Napier.d("mist")
                        } else if (weatherName.contains("haze", ignoreCase = true)) {
                            weatherImage(
                                cityName = locationName,
                                painter = painterResource(resource = Res.drawable.rain),
                                imgUrl = "https://raw.githubusercontent.com/yeshuwahane/weatherImages/main/weather_images/haze.jpeg",
                                weatherName = weatherName,
                                temperature = temp,
                            )
                            Napier.d("haze")
                        } else if (weatherName.contains("snow", ignoreCase = true)) {
                            weatherImage(
                                cityName = locationName,
                                painter = painterResource(resource = Res.drawable.rain),
                                imgUrl = "https://raw.githubusercontent.com/yeshuwahane/weatherImages/main/weather_images/snow.jpeg",
                                weatherName = weatherName,
                                temperature = temp,
                            )
                            Napier.d("snow")
                        } else if (weatherName.contains("thunderstorm", ignoreCase = true)) {
                            weatherImage(
                                cityName = locationName,
                                painter = painterResource(resource = Res.drawable.rain),
                                imgUrl = "https://raw.githubusercontent.com/yeshuwahane/weatherImages/main/weather_images/thunderstorm.jpeg",
                                weatherName = weatherName,
                                temperature = temp,
                            )
                            Napier.d("thunderstorm")
                        }
                        else{
                            Column(
                                Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text("search city, to get Weather", fontWeight = FontWeight.Bold)

                            }
                        }


                    }


                }
            }
        }


    }

    @Composable
    fun weatherImage(
        cityName: String,
        painter: Painter,
        imgUrl: String,
        weatherName: String,
        temperature: String,

    ) {

        KamelImage(
            asyncPainterResource(imgUrl),
            "",
            modifier = Modifier.fillMaxHeight(),
            contentScale = ContentScale.Crop
        )
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val isRotated = true
            val rotationAngle by animateFloatAsState(
                targetValue = if (isRotated) 360F else 0f,
                animationSpec = tween(durationMillis = 2500)
            )

            Image(painter,
                null,
                modifier = Modifier
                    .rotate(rotationAngle)
            )
            Text("Location: $cityName", fontWeight = FontWeight.Bold)
            Text("Weather: $weatherName", fontWeight = FontWeight.Bold)
            Text("temperature: $temperature°C", fontWeight = FontWeight.Bold)
        }

    }

    }

