package com.caneryilmazapps.foreksapp.data.models.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CoinResponse(
    @SerializedName("l")
    val coins: List<Coin>,

    @SerializedName("z")
    val z: String
) : Serializable {
    override fun toString(): String {
        return "CoinResponse(coins=$coins, z='$z')"
    }
}

data class Coin(
    val pdd: String,
    val def: String,
    val low: String,
    val hig: String,
    val clo: String,
    val buy: String,
    val ddi: String,
    val cod: String,
    val sel: String,
    val pdc: String,
    val las: String,
    val tke: String,
    val rtp: Boolean,
) : Serializable {
    override fun toString(): String {
        return "Coin(pdd=$pdd, def='$def', low=$low, hig=$hig, clo='$clo', buy=$buy, ddi=$ddi, cod='$cod', sel=$sel, pdc=$pdc, las=$las, tke='$tke', rtp=$rtp)"
    }
}
