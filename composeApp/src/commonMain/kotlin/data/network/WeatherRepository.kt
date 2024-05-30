package data.network

import data.model.CityWeatherModel
import data.utils.DataResource

interface WeatherRepository {
    suspend fun getWeatherByCity():DataResource<CityWeatherModel>
    suspend fun getWeatherByCoordinated(long:String,lat:String):DataResource<CityWeatherModel>

    suspend fun setCity(city: String)
    suspend fun getCity():String
}