package presentation

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import data.network.WeatherRepositoryImpl
import io.github.aakira.napier.Napier
import io.ktor.util.date.GMTDate
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.TimeSource

class MainViewModel(
    private val weatherRepositoryImpl: WeatherRepositoryImpl
) : ScreenModel {

   private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState = _uiState.asStateFlow()

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
            in 18..23 -> "Evening"
            else -> "Invalid hour"
        }
    }


}

