package com.caneryilmazapps.foreksapp.ui.main.fragments.list

import com.caneryilmazapps.foreksapp.data.remote.CoinApi
import javax.inject.Inject

class CoinListPresenter @Inject constructor(private val coinApi: CoinApi) :
    CoinListContract.CoinListPresenter {

    private lateinit var view: CoinListContract.CoinListView

    fun attachView(view: CoinListContract.CoinListView) {
        this.view = view
    }

    override suspend fun getCoinList() {

        val response = coinApi.getCoinList()

        if (response.isSuccessful) {
            if (response.body()?.coins?.isEmpty()!!) {
                view.onFail("Tekrar Dene")
            } else {
                view.onSuccess(response.body()!!)
            }
        } else {
            view.onFail(response.message())
        }
    }
}