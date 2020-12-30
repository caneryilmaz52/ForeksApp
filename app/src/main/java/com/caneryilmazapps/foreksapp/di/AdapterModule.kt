package com.caneryilmazapps.foreksapp.di

import com.caneryilmazapps.foreksapp.adapters.CoinListAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
object AdapterModule {

    @Provides
    fun provideCoinListAdapter(): CoinListAdapter = CoinListAdapter()
}