package com.fk.codesample.network.dto

data class CatDto(
    val id: String?,
    val url: String?,
    val breeds: List<BreedDto>?
)