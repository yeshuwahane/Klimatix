package data.network

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set
import data.Constant
import data.model.CityWeatherModel
import data.model.forecast.CityForecastModel
import data.utils.DataResource
import data.utils.apiCall
import domain.WeatherRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class WeatherRepositoryImpl(
    private val networkClient:HttpClient
): WeatherRepository {
    val settings: Settings = Settings()
    override suspend fun getCityWeather(): DataResource<CityWeatherModel> {
        return apiCall {
            val cityName = getCity()
                networkClient.get(Constant.CURRENT_WEATHER_URL){
                    url {
                        parameters.append("key",Constant.API_KEY)
                        parameters.append("q",cityName)
                        parameters.append("aqi","yes")
                    }
                }
        }
    }

    override suspend fun getCityForecast(): DataResource<CityForecastModel> {
      return  apiCall {
            val city = getCity()
            networkClient.get(Constant.WEATHER_FORECAST_URL) {
                url{
                    parameters.append("key",Constant.API_KEY)
                    parameters.append("q",city)
                    parameters.append("aqi","yes")
                }
            }
        }
    }

    override suspend fun setCity(city: String) {
        settings.set(Constant.CITY,city)
    }

    override suspend fun getCity(): String {
        val city = settings.get(Constant.CITY,"pune")
        return city
    }
}


