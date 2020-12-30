package com.caneryilmazapps.foreksapp.data.remote

import com.caneryilmazapps.foreksapp.data.models.response.CoinDetailResponse
import com.caneryilmazapps.foreksapp.data.models.response.CoinResponse
import com.caneryilmazapps.foreksapp.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinApi {

    @GET(Constants.getCoinList)
    suspend fun getCoinList(): Response<CoinResponse>

    @GET("${Constants.getCoinDetail}{cod}")
    suspend fun getCoinDetail(@Query("cod") code: String): Response<CoinDetailResponse>
}