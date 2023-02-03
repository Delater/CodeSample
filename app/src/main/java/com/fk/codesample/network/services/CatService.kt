package com.fk.codesample.network.services

import com.fk.codesample.network.dto.CatDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CatService {

    @GET("images/search")
    fun getCats(
        @Query("has_breeds") hasBreeds: Int = 1,
        @Query("limit") limit: Int = 100,

    ): Single<List<CatDto>>
}