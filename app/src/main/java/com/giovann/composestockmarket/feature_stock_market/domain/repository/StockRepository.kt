package com.giovann.composestockmarket.feature_stock_market.domain.repository

import com.giovann.composestockmarket.feature_stock_market.data.remote.StockAPI
import com.giovann.composestockmarket.feature_stock_market.data.remote.dto.CompanyInfoDto
import com.giovann.composestockmarket.feature_stock_market.domain.model.CompanyInfo
import com.giovann.composestockmarket.feature_stock_market.domain.model.CompanyListing
import com.giovann.composestockmarket.feature_stock_market.domain.model.IntradayInfo
import com.giovann.composestockmarket.feature_stock_market.util.Resource
import kotlinx.coroutines.flow.Flow
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockRepository {

    suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>>

    suspend fun getIntradayInfo(
        symbol: String
    ): Resource<List<IntradayInfo>>

    suspend fun getCompanyInfo(
        symbol: String
    ): Resource<CompanyInfo>
}