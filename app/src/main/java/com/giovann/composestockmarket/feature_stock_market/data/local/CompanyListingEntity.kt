package com.giovann.composestockmarket.feature_stock_market.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CompanyListingEntity(
    val name: String,
    val symbol: String,
    val exchange: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
)