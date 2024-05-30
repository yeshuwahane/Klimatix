package data.network

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set
import data.Constant
import data.model.CityWeatherModel
import data.utils.DataResource
import data.utils.apiCall
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.url

class WeatherRepositoryImpl(
    private val networkClient:HttpClient
):WeatherRepository {

    val settings: Settings = Settings()

    override suspend fun getWeatherByCity(
    ): DataResource<CityWeatherModel> {
        return apiCall {
            val cityName = getCity()
            networkClient.get(Constant.CITY_WEATHER_URL){
                url{
                    parameters.append("q",cityName)
                    parameters.append("appid",Constant.API_KEY)
                    parameters.append("units","metric")
                }

            }
        }
    }

    override suspend fun getWeatherByCoordinated(
        long: String,
        lat: String
    ): DataResource<CityWeatherModel> {
        return apiCall {
            networkClient.get(Constant.CITY_WEATHER_URL) {
                url{
                    parameters.append("lat",lat)
                    parameters.append("lon",long)
                    parameters.append("appid",Constant.API_KEY)
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


