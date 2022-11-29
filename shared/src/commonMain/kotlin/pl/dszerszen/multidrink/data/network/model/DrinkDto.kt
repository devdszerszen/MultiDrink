package pl.dszerszen.multidrink.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DrinkDto(
    @SerialName("idDrink") val id: String,
    @SerialName("strDrink") val name: String,
    @SerialName("strDrinkThumb") val image: String?
)