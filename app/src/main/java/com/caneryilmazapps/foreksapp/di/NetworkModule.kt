package com.caneryilmazapps.foreksapp.di

import com.caneryilmazapps.foreksapp.data.remote.CoinApi
import com.caneryilmazapps.foreksapp.data.remote.RetrofitInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofitApi(): CoinApi = RetrofitInstance.api
}