package com.caneryilmazapps.foreksapp.ui.main.fragments.list

import com.caneryilmazapps.foreksapp.data.models.response.CoinResponse

interface CoinListContract {

    interface CoinListView {
        fun onSuccess(coinResponse: CoinResponse)
        fun onFail(message: String)
    }

    interface CoinListPresenter {
        suspend fun getCoinList()
    }
}