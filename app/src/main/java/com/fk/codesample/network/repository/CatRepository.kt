package com.fk.codesample.network.repository

import com.fk.codesample.entities.Cat
import com.fk.codesample.mappers.CatDtoMapper
import com.fk.codesample.network.services.CatService
import io.reactivex.Single
import javax.inject.Inject

class CatRepositoryImpl @Inject constructor(
    private val catService: CatService,
    private val catDtoMapper: CatDtoMapper
) : CatRepository {
    override fun getCats(): Single<List<Cat>> {
        return catService.getCats()
            .map { catList ->
                catList.mapNotNull { catDtoMapper.map(it) } }
    }
}

interface CatRepository {
    fun getCats(): Single<List<Cat>>
}
