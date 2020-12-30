package com.caneryilmazapps.foreksapp.ui.main.fragments.detail

import com.caneryilmazapps.foreksapp.data.models.response.CoinDetailResponse

interface CoinDetailContract {

    interface CoinDetailsView {
        fun onSuccess(coinDetailResponse: CoinDetailResponse)
        fun onSuccessAddFavorite()
        fun onSuccessDeleteFavorite()
        fun onFail(message: String)
        fun onFailAddFavorite(message: String)
        fun onFailDeleteFavorite(message: String)
    }

    interface CoinDetailPresenter {
        suspend fun getCoinDetail(coinCode: String)
        suspend fun saveFavoriteCoin(coinCode: String)
        suspend fun deleteFavoriteCoin(coinCode: String)
    }
}