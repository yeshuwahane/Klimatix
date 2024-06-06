package data.model.forecast


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CityForecastModel(
    @SerialName("current")
    val current: Current,
    @SerialName("forecast")
    val forecast: Forecast,
    @SerialName("location")
    val location: Location
)



@Serializable
data class Current(
    @SerialName("air_quality")
    val airQuality: AirQuality,
    @SerialName("cloud")
    val cloud: Int,
    @SerialName("condition")
    val condition: Condition,
    @SerialName("dewpoint_c")
    val dewpointC: Double,
    @SerialName("dewpoint_f")
    val dewpointF: Double,
    @SerialName("feelslike_c")
    val feelslikeC: Double,
    @SerialName("feelslike_f")
    val feelslikeF: Double,
    @SerialName("gust_kph")
    val gustKph: Double,
    @SerialName("gust_mph")
    val gustMph: Double,
    @SerialName("heatindex_c")
    val heatindexC: Double,
    @SerialName("heatindex_f")
    val heatindexF: Double,
    @SerialName("humidity")
    val humidity: Int,
    @SerialName("is_day")
    val isDay: Int,
    @SerialName("last_updated")
    val lastUpdated: String,
    @SerialName("last_updated_epoch")
    val lastUpdatedEpoch: Int,
    @SerialName("precip_in")
    val precipIn: Double,
    @SerialName("precip_mm")
    val precipMm: Double,
    @SerialName("pressure_in")
    val pressureIn: Double,
    @SerialName("pressure_mb")
    val pressureMb: Double,
    @SerialName("temp_c")
    val tempC: Double,
    @SerialName("temp_f")
    val tempF: Double,
    @SerialName("uv")
    val uv: Double,
    @SerialName("vis_km")
    val visKm: Double,
    @SerialName("vis_miles")
    val visMiles: Double,
    @SerialName("wind_degree")
    val windDegree: Int,
    @SerialName("wind_dir")
    val windDir: String,
    @SerialName("wind_kph")
    val windKph: Double,
    @SerialName("wind_mph")
    val windMph: Double,
    @SerialName("windchill_c")
    val windchillC: Double,
    @SerialName("windchill_f")
    val windchillF: Double
)

@Serializable
data class Condition(
    @SerialName("code")
    val code: Int,
    @SerialName("icon")
    val icon: String,
    @SerialName("text")
    val text: String
)


@Serializable
data class Location(
    @SerialName("country")
    val country: String,
    @SerialName("lat")
    val lat: Double,
    @SerialName("localtime")
    val localtime: String,
    @SerialName("localtime_epoch")
    val localtimeEpoch: Int,
    @SerialName("lon")
    val lon: Double,
    @SerialName("name")
    val name: String,
    @SerialName("region")
    val region: String,
    @SerialName("tz_id")
    val tzId: String
)