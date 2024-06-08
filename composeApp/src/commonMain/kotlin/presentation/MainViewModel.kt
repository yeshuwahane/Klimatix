package presentation

import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import data.network.WeatherRepositoryImpl
import data.utils.DataResource
import io.github.aakira.napier.Napier
import io.ktor.util.date.GMTDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class MainViewModel(
    private val weatherRepositoryImpl: WeatherRepositoryImpl
) : ScreenModel {

    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState = _uiState.asStateFlow()
    private val _forecastUiState = MutableStateFlow(WeatherForecastUiState())
    val forecastUiState = _forecastUiState.asStateFlow()


    fun getWeather() {
        screenModelScope.launch {
            val response = weatherRepositoryImpl.getCityWeather()
            if (response.isSuccess()) {
                val dayStatus = getTime()
                _uiState.update {
                    it.copy(
                        weatherCondition = response.data?.current?.condition?.text.toString(),
                        iconCode = response.data?.current?.condition?.icon.toString(),
                        temp = response.data?.current?.tempC.toString() + "°",
                        cityName = response.data?.location?.name.toString(),
                        uvIndex = response.data?.current?.uv ?: 0.0,
                        feelslikeTemp = response.data?.current?.feelslikeC.toString() + "°",
                        humidity = response.data?.current?.humidity.toString(),
                        windDirection = response.data?.current?.windDir.toString(),
                        windKph = response.data?.current?.windKph.toString(),
                        isDay = response.data?.current?.isDay,
                        last_updated = response.data?.current?.lastUpdated.toString(),
                        pressure_mb = response.data?.current?.pressureMb ?: 0.0,
                        visibilityKm = response.data?.current?.visKm ?: 0.0,
                        airQualityIndex = response.data?.current?.airQuality?.usEpaIndex ?: 0,
                        dayStatus = dayStatus
                    )
                }
            } else if (response.isError()) {
                Napier.d("MVM: Failed -----------------")

            }

            Napier.d("response: ${response.isLoading()}")
            Napier.d("response: ${response.data?.current?.condition?.text.toString()}")
        }
    }

    fun getForecast() {
        _forecastUiState.update {
            it.copy(
                weatherForecastResource = DataResource.loading()
            )
        }
        screenModelScope.launch {
            val response = weatherRepositoryImpl.getCityForecast()

            if (response.isSuccess() && response.data?.forecast?.forecastday?.isNotEmpty() == true) {
                _forecastUiState.update {
                    it.copy(
                        weatherForecastResource = response
                    )
                }
                _uiState.update {
                    it.copy(
                        weatherCondition = response.data.current.condition.text,
                        iconCode = response.data.current.condition.icon,
                        temp = response.data.current.tempC.toString() + "°",
                        cityName = response.data.location.name,
                        uvIndex = response.data.current.uv,
                        feelslikeTemp = response.data.current.feelslikeC.toString() + "°",
                        humidity = response.data.current.humidity.toString(),
                        windDirection = response.data.current.windDir,
                        windKph = response.data.current.windKph.toString(),
                        isDay = response.data.current.isDay,
                        last_updated = response.data.current.lastUpdated,
                        pressure_mb = response.data.current.pressureMb,
                        visibilityKm = response.data.current.visKm,
                        airQualityIndex = response.data.current.airQuality.usEpaIndex,
                    )
                }
            } else {
                _forecastUiState.update {
                    it.copy(
                        weatherForecastResource = DataResource.error(Throwable("No Connection, please Restart when connection in back"))
                    )
                }
            }

        }

    }


    fun setCity(city: String) {
        screenModelScope.launch {
            weatherRepositoryImpl.setCity(city)
            getForecast()
        }
    }

    fun getTime(): String {
        val time = GMTDate().hours
        Napier.d("Time: $time")
        val dayStatus = getTimeOfDay(time)
        return dayStatus
    }

    fun getTimeOfDay(hour: Int): String {
        return when (hour) {
            in 0..5 -> "Night"
            in 6..11 -> "Morning"
            in 12..17 -> "Afternoon"
            in 18..23 -> "Evening"
            else -> "Invalid hour"
        }
    }

    fun getLocalTime(): Int {
        val now: Instant = Clock.System.now()
        val thisTime: LocalTime = now.toLocalDateTime(TimeZone.currentSystemDefault()).time
        val currentHour = thisTime.hour

        Napier.d("Current Hour: $currentHour")

        return currentHour
    }


}









/*class MainViewModel(
    private val weatherRepositoryImpl: WeatherRepositoryImpl
) : ScreenModel {

   private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState = _uiState.asStateFlow()
    private val _forecastUiState = MutableStateFlow(WeatherForecastUiState())
    val forecastUiState = _forecastUiState.asStateFlow()



    fun getWeather() {
        screenModelScope.launch {
            val response = weatherRepositoryImpl.getCityWeather()
            if (response.isSuccess()){
                val dayStatus = getTime()
                _uiState.update {
                    it.copy(
                        weatherCondition = response.data?.current?.condition?.text.toString(),
                        iconCode = response.data?.current?.condition?.icon.toString(),
                        temp = response.data?.current?.tempC.toString()+"°",
                        cityName = response.data?.location?.name.toString(),
                        uvIndex = response.data?.current?.uv ?: 0.0,
                        feelslikeTemp = response.data?.current?.feelslikeC.toString()+"°",
                        humidity = response.data?.current?.humidity.toString(),
                        windDirection = response.data?.current?.windDir.toString(),
                        windKph = response.data?.current?.windKph.toString(),
                        isDay = response.data?.current?.isDay,
                        last_updated = response.data?.current?.lastUpdated.toString(),
                        pressure_mb = response.data?.current?.pressureMb ?:0.0,
                        visibilityKm = response.data?.current?.visKm ?:0.0,
                        airQualityIndex = response.data?.current?.airQuality?.usEpaIndex?:0,
                        dayStatus = dayStatus
                    )
                }
            } else if (response.isError()){
                Napier.d("MVM: Failed -----------------")

            }

            Napier.d("response: ${response.isLoading()}")
            Napier.d("response: ${response.data?.current?.condition?.text.toString()}")
        }
    }

    fun getForecast(){
        _forecastUiState.update {
            it.copy(
                weatherForecastResource = DataResource.loading()
            )
        }
        screenModelScope.launch {
            val response = weatherRepositoryImpl.getCityForecast()

            if (response.isSuccess() && response.data?.forecast?.forecastday?.isNotEmpty() == true){
                _forecastUiState.update {
                    it.copy(
                        weatherForecastResource = response
                    )
                }
                _uiState.update {
                    it.copy(
                        weatherCondition = response.data.current.condition.text,
                        iconCode = response.data.current.condition.icon,
                        temp = response.data.current.tempC.toString()+"°",
                        cityName = response.data.location.name,
                        uvIndex = response.data.current.uv,
                        feelslikeTemp = response.data.current.feelslikeC.toString()+"°",
                        humidity = response.data.current.humidity.toString(),
                        windDirection = response.data.current.windDir,
                        windKph = response.data.current.windKph.toString(),
                        isDay = response.data.current.isDay,
                        last_updated = response.data.current.lastUpdated,
                        pressure_mb = response.data.current.pressureMb,
                        visibilityKm = response.data.current.visKm,
                        airQualityIndex = response.data.current.airQuality.usEpaIndex,
                    )
                }
            }else{
                _forecastUiState.update {
                    it.copy(
                        weatherForecastResource = DataResource.error(Throwable("No Connection, please Restart when connection in back"))
                    )
                }
            }

        }

    }



    fun setCity(city: String) {
        screenModelScope.launch {
            weatherRepositoryImpl.setCity(city)
            getWeather()
        }
    }

    fun getTime():String{
        val time = GMTDate().hours
        Napier.d("Time: $time")
        val dayStatus = getTimeOfDay(time)
        return dayStatus
    }

    fun getTimeOfDay(hour: Int): String {
        return when (hour) {
            in 0..5 -> "Night"
            in 6..11 -> "Morning"
            in 12..17 -> "Afternoon"
            in 18..23 -> "Night"
            else -> "Invalid hour"
        }
    }

    fun getLocalTime():Int{
        val now: Instant = Clock.System.now()
        val thisTime: LocalTime = now.toLocalDateTime(TimeZone.currentSystemDefault()).time
        val currentHour = thisTime.hour

        Napier.d("Current Hour: $currentHour")

        return currentHour
    }




}*/



