package com.caneryilmazapps.foreksapp.data.models.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FavoriteCoins")
data class FavoriteCoinEntity(
    @PrimaryKey @ColumnInfo(name = "coinCode")
    val coinCode: String
)