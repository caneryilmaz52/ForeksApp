package com.caneryilmazapps.foreksapp.ui.main.fragments.favorite

import com.caneryilmazapps.foreksapp.data.models.local.FavoriteCoinEntity
import com.caneryilmazapps.foreksapp.data.models.response.CoinResponse

interface FavoriteCoinsContract {

    interface FavoriteCoinsView {
        fun onSuccessGetFavoriteCoins(favoriteCoinEntity: List<FavoriteCoinEntity>)
        fun onSuccessGetCoinList(coinResponse: CoinResponse)
        fun onFailGetFavoriteCoins(message: String)
        fun onFailGetCoinList(message: String)
    }

    interface FavoriteCoinsPresenter {
        suspend fun getCoinList()
        suspend fun getFavoriteCoins()
    }
}