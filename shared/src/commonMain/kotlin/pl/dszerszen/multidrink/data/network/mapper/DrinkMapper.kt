package pl.dszerszen.multidrink.data.network.mapper

import pl.dszerszen.multidrink.data.network.model.DrinkDto
import pl.dszerszen.multidrink.domain.model.Drink

fun DrinkDto.toDomain(): Drink {
    return Drink(
        id = id,
        name = name,
        image = image
    )
}