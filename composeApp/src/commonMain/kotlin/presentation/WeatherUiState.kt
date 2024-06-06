package presentation

import data.model.AirQuality
import data.model.forecast.CityForecastModel
import data.utils.DataResource

data class WeatherUiState(
    val weatherCondition:String = "",
    val temp:String = "",
    val cityName:String ="",
    val iconCode:String="",
    val uvIndex:Double = 0.0,
    val feelslikeTemp:String= "",
    val humidity:String ="",
    val windDirection:String="",
    val windKph:String ="",
    val isDay: Int? = 1,
    val last_updated:String ="",
    val pressure_mb:Double = 0.0,
    val visibilityKm:Double = 0.0,
    val airQualityIndex: Int =0,
    val dayStatus:String=""
    )

data class WeatherForecastUiState(
    val weatherForecastResource: DataResource<CityForecastModel> = DataResource.loading()
)