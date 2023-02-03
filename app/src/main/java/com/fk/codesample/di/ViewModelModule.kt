package com.fk.codesample.di

import com.fk.codesample.util.AppSchedulers
import com.fk.codesample.util.AppSchedulersImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ViewModelModule {

    @Provides
    fun provideSchedulers(): AppSchedulers = AppSchedulersImpl()
}