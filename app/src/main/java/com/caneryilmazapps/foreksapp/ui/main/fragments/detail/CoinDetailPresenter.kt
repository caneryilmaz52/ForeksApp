package com.caneryilmazapps.foreksapp.ui.main.fragments.detail

import com.caneryilmazapps.foreksapp.data.local.CoinDatabase
import com.caneryilmazapps.foreksapp.data.models.local.FavoriteCoinEntity
import com.caneryilmazapps.foreksapp.data.remote.CoinApi
import javax.inject.Inject

class CoinDetailPresenter @Inject constructor(
    private val coinApi: CoinApi,
    private val coinDatabase: CoinDatabase
) :
    CoinDetailContract.CoinDetailPresenter {

    private lateinit var view: CoinDetailContract.CoinDetailsView

    fun attachView(view: CoinDetailContract.CoinDetailsView) {
        this.view = view
    }

    override suspend fun getCoinDetail(coinCode: String) {

        val response = coinApi.getCoinDetail(coinCode)

        if (response.isSuccessful) {
            if (response.body()?.coinDetail?.isEmpty()!!) {
                view.onFail("Tekrar Dene")
            } else {
                view.onSuccess(response.body()!!)
            }
        } else {
            view.onFail(response.message())
        }

    }

    override suspend fun saveFavoriteCoin(coinCode: String) {
        val favoriteCoinEntity = FavoriteCoinEntity(coinCode)
        val response = coinDatabase.coinDao().insertFavoriteCoin(favoriteCoinEntity)

        if (response >= 0) {
            view.onSuccessAddFavorite()
        } else {
            view.onFail("Bir hata oluştu")
        }
    }

    override suspend fun deleteFavoriteCoin(coinCode: String) {
        val favoriteCoinEntity = FavoriteCoinEntity(coinCode)
        val response = coinDatabase.coinDao().deleteFavoriteCoin(favoriteCoinEntity)

        if (response >= 0) {
            view.onSuccessDeleteFavorite()
        } else {
            view.onFail("Bir hata oluştu")
        }
    }
}