package com.caneryilmazapps.foreksapp.di

import com.caneryilmazapps.foreksapp.data.local.CoinDatabase
import com.caneryilmazapps.foreksapp.data.remote.CoinApi
import com.caneryilmazapps.foreksapp.ui.main.fragments.detail.CoinDetailPresenter
import com.caneryilmazapps.foreksapp.ui.main.fragments.favorite.FavoriteCoinsPresenter
import com.caneryilmazapps.foreksapp.ui.main.fragments.list.CoinListPresenter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object PresenterModule {

    @Singleton
    @Provides
    fun provideCoinListPresenter(coinApi: CoinApi) = CoinListPresenter(coinApi)

    @Singleton
    @Provides
    fun provideFavoriteCoinsPresenter(coinApi: CoinApi, coinDatabase: CoinDatabase) =
        FavoriteCoinsPresenter(coinApi, coinDatabase)

    @Singleton
    @Provides
    fun provideCoinDetailPresenter(coinApi: CoinApi, coinDatabase: CoinDatabase) =
        CoinDetailPresenter(coinApi, coinDatabase)

}