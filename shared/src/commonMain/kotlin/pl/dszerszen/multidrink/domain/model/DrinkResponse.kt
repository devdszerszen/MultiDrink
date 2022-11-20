package pl.dszerszen.multidrink.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class DrinkResponse(
    val drinks: List<Drink>
)