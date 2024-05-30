package presentation

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import cafe.adriel.voyager.core.registry.screenModule
import data.Constant
import data.network.WeatherRepositoryImpl
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val weatherRepositoryImpl: WeatherRepositoryImpl
) : ScreenModel {

   private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState = _uiState.asStateFlow()


    fun getWeather() {
        screenModelScope.launch {
            val response = weatherRepositoryImpl.getWeatherByCity()
            _uiState.update {
                it.copy(
                    weatherCondition = response.data?.weather?.get(0)?.main.toString(),
                    temp = response.data?.main?.temp.toString(),
                    cityName = response.data?.name.toString()
                )
            }
            Napier.d("response: ${response.isLoading()}")
            Napier.d("response: ${response.data?.weather.toString()}")
        }
    }

    fun setCity(city: String) {
        screenModelScope.launch {
            weatherRepositoryImpl.setCity(city)
            getWeather()
        }
    }

}

