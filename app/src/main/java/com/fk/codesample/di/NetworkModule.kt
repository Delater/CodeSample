package com.fk.codesample.di

import com.fk.codesample.BuildConfig
import com.fk.codesample.network.AuthorizationHeaderInterceptor
import com.fk.codesample.mappers.CatDtoMapper
import com.fk.codesample.network.repository.CatRepository
import com.fk.codesample.network.repository.CatRepositoryImpl
import com.fk.codesample.network.services.CatService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://api.thecatapi.com/v1/"

    @Provides
    fun provideOkHttpClient(
        authorizationHeaderInterceptor: AuthorizationHeaderInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authorizationHeaderInterceptor)
            .build()
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Provides
    fun provideAuthorizationHeaderInterceptor() =
        AuthorizationHeaderInterceptor(BuildConfig.CAT_REST_API_KEY)

    @Provides
    fun provideUserService(retrofit: Retrofit): CatService =
        retrofit.create(CatService::class.java)

    @Provides
    fun provideCatRepository(catService: CatService, catDtoMapper: CatDtoMapper): CatRepository =
        CatRepositoryImpl(catService, catDtoMapper)
}