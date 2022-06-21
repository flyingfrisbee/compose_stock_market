package com.giovann.composestockmarket.feature_stock_market.data.remote

import com.giovann.composestockmarket.feature_stock_market.data.remote.dto.CompanyInfoDto
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockAPI {

    @GET("/query")
    suspend fun getListings(
        @Query("function") function: String = "LISTING_STATUS",
        @Query("apikey") APIKey: String = API_KEY
    ): ResponseBody

    @GET("/query")
    suspend fun getIntradayInfo(
        @Query("function") function: String = "TIME_SERIES_INTRADAY",
        @Query("interval") interval: String = "60min",
        @Query("datatype") dataType: String = "csv",
        @Query("symbol") symbol: String,
        @Query("apikey") APIKey: String = API_KEY
    ): ResponseBody

    @GET("/query")
    suspend fun getCompanyInfo(
        @Query("function") function: String = "OVERVIEW",
        @Query("symbol") symbol: String,
        @Query("apikey") APIKey: String = API_KEY
    ): CompanyInfoDto

    companion object {
        const val API_KEY = "M0L1SE77LR5W9KNJ"
        const val BASE_URL = "https://alphavantage.co"
    }
}