package domain

import data.model.CityWeatherModel
import data.model.forecast.CityForecastModel
import data.utils.DataResource


interface WeatherRepository {

    suspend fun getCityWeather():DataResource<CityWeatherModel>

    suspend fun getCityForecast():DataResource<CityForecastModel>

    suspend fun setCity(city: String)
    suspend fun getCity():String
}