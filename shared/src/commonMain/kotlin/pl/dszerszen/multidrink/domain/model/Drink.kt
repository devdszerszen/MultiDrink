package pl.dszerszen.multidrink.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Drink(
    @SerialName("idDrink") val id: String,
    @SerialName("strDrink") val name: String
)