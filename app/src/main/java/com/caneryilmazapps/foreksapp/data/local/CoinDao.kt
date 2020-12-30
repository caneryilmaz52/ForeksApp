package com.caneryilmazapps.foreksapp.data.local

import androidx.room.*
import com.caneryilmazapps.foreksapp.data.models.local.FavoriteCoinEntity

@Dao
interface CoinDao {

    @Query("SELECT * FROM FavoriteCoins")
    suspend fun getFavoriteCoinsAll(): List<FavoriteCoinEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteCoin(coin: FavoriteCoinEntity): Long

    @Delete
    suspend fun deleteFavoriteCoin(coin: FavoriteCoinEntity): Int
}