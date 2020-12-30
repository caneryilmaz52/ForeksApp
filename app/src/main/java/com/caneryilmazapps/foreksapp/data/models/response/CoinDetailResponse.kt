package com.caneryilmazapps.foreksapp.data.models.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CoinDetailResponse(
    @SerializedName("cod")
    val cod: String,

    @SerializedName("def")

    val def: String,
    @SerializedName("d")
    val coinDetail: List<CoinDetail>,
) : Serializable {
    override fun toString(): String {
        return "CoinDetailResponse(cod='$cod', def='$def', coinDetail=$coinDetail)"
    }
}

data class CoinDetail(
    @SerializedName("clo")
    val clo: String,

    @SerializedName("fields")
    val fields: Fields,

    ) : Serializable {
    override fun toString(): String {
        return "CoinDetail(clo='$clo', fields=$fields)"
    }
}

data class Fields(
    @SerializedName("las")
    val las: String,

    @SerializedName("buy")
    val buy: String,

    @SerializedName("sel")
    val sel: String,

    @SerializedName("low")
    val low: String,

    @SerializedName("hig")
    val hig: String,

    @SerializedName("ddi")
    val ddi: String,

    @SerializedName("pdd")
    val pdd: String,
) : Serializable {
    override fun toString(): String {
        return "Fields(las=$las, buy=$buy, sel=$sel, low=$low, hig=$hig, ddi=$ddi, pdd=$pdd)"
    }
}



