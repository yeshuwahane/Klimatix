package data.model.forecast


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AirQuality(
    @SerialName("co")
    val co: Double,
    @SerialName("gb-defra-index")
    val gbDefraIndex: Int,
    @SerialName("no2")
    val no2: Double,
    @SerialName("o3")
    val o3: Double,
    @SerialName("pm10")
    val pm10: Double,
    @SerialName("pm2_5")
    val pm25: Double,
    @SerialName("so2")
    val so2: Double,
    @SerialName("us-epa-index")
    val usEpaIndex: Int
)