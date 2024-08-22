package com.abbesolo.fdjapp.di

import com.abbesolo.fdjapp.data.api.SportsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by HOUNSA ROMUALD on 21/08/24.
 * Copyright (c) 2024 abbesolo. All rights reserved.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.thesportsdb.com/api/v1/json/50130162/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideSportsApi(retrofit: Retrofit): SportsApi {
        return retrofit.create(SportsApi::class.java)
    }
}
