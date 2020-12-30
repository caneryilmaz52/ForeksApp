package com.caneryilmazapps.foreksapp.di

import android.content.Context
import com.caneryilmazapps.foreksapp.data.local.CoinDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideCoinDatabase(@ApplicationContext appContext: Context) =
        CoinDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideCoinDao(coinDatabase: CoinDatabase) = coinDatabase.coinDao()
}