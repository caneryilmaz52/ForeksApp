package com.caneryilmazapps.foreksapp.ui.main.fragments.favorite

import com.caneryilmazapps.foreksapp.data.local.CoinDatabase
import com.caneryilmazapps.foreksapp.data.remote.CoinApi
import javax.inject.Inject

class FavoriteCoinsPresenter @Inject constructor(
    private val coinApi: CoinApi,
    private val coinDatabase: CoinDatabase
) :
    FavoriteCoinsContract.FavoriteCoinsPresenter {

    private lateinit var view: FavoriteCoinsContract.FavoriteCoinsView

    fun attachView(view: FavoriteCoinsContract.FavoriteCoinsView) {
        this.view = view
    }

    override suspend fun getCoinList() {

        val response = coinApi.getCoinList()

        if (response.isSuccessful) {
            if (response.body()?.coins?.isEmpty()!!) {
                view.onFailGetCoinList("Tekrar Dene")
            } else {
                view.onSuccessGetCoinList(response.body()!!)
            }
        } else {
            view.onFailGetCoinList(response.message())
        }
    }

    override suspend fun getFavoriteCoins() {

        val response = coinDatabase.coinDao().getFavoriteCoinsAll()

        if (response.isNotEmpty()) {
            view.onSuccessGetFavoriteCoins(response)
        } else {
            view.onFailGetFavoriteCoins("Favoriler boş")
        }
    }
}