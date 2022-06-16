package com.giovann.composestockmarket.feature_stock_market.data.remote

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockAPI {

    @GET("/query")
    suspend fun getListings(
        @Query("function") function: String = "LISTING_STATUS",
        @Query("apikey") APIKey: String = API_KEY
    ): ResponseBody

    companion object {
        const val API_KEY = "M0L1SE77LR5W9KNJ"
        const val BASE_URL = "https://alphavantage.co"
    }
}