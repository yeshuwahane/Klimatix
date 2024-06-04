package domain

import data.model.CityWeatherModel
import data.utils.DataResource


interface WeatherRepository {

    suspend fun getCityWeather():DataResource<CityWeatherModel>

    suspend fun setCity(city: String)
    suspend fun getCity():String
}