package pl.dszerszen.multidrink.data.network.model

import kotlinx.serialization.Serializable

@Serializable
data class DrinkResponseDto(
    val drinks: List<DrinkDto>
)