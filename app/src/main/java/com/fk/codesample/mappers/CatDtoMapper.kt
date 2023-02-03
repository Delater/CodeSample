package com.fk.codesample.mappers

import com.fk.codesample.entities.Cat
import com.fk.codesample.network.dto.CatDto
import javax.inject.Inject

class CatDtoMapper @Inject constructor() {

    fun map(catDto: CatDto): Cat? {
        if (catDto.breeds == null || catDto.breeds.isEmpty()) {
            return null
        } else {
            val breed = catDto.breeds.first()
            breed.apply {
                if (name != null &&
                    temperament != null &&
                    origin != null &&
                    catDto.id != null &&
                    catDto.url != null
                ) {
                    return Cat(
                        id = catDto.id,
                        imageUrl = catDto.url,
                        breedName = name,
                        breedTemperament = temperament,
                        origin = origin
                    )
                }
            }
            return null
        }
    }
}