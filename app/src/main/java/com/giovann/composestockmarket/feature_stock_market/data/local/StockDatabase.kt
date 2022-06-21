package com.giovann.composestockmarket.feature_stock_market.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CompanyListingEntity::class],
    version = 1
)
abstract class StockDatabase : RoomDatabase() {

    abstract val DAO: StockDAO

    companion object {
        const val DATABASE_NAME = "STOCK_DATABASE"
    }
}