package com.caneryilmazapps.foreksapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.caneryilmazapps.foreksapp.data.models.local.FavoriteCoinEntity

@Database(entities = [FavoriteCoinEntity::class], version = 1)
abstract class CoinDatabase : RoomDatabase() {
    abstract fun coinDao(): CoinDao

    companion object {
        @Volatile
        private var instance: CoinDatabase? = null

        fun getDatabase(context: Context): CoinDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, CoinDatabase::class.java, "FavoriteCoinDatabase")
                .fallbackToDestructiveMigration()
                .build()
    }
}