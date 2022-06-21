package com.giovann.composestockmarket.feature_stock_market.data.repository

import com.giovann.composestockmarket.feature_stock_market.data.csv.CSVParser
import com.giovann.composestockmarket.feature_stock_market.data.csv.IntradayInfoParser
import com.giovann.composestockmarket.feature_stock_market.data.local.StockDAO
import com.giovann.composestockmarket.feature_stock_market.data.local.StockDatabase
import com.giovann.composestockmarket.feature_stock_market.data.mapper.toCompanyInfo
import com.giovann.composestockmarket.feature_stock_market.data.mapper.toCompanyListing
import com.giovann.composestockmarket.feature_stock_market.data.mapper.toCompanyListingEntity
import com.giovann.composestockmarket.feature_stock_market.data.remote.StockAPI
import com.giovann.composestockmarket.feature_stock_market.domain.model.CompanyInfo
import com.giovann.composestockmarket.feature_stock_market.domain.model.CompanyListing
import com.giovann.composestockmarket.feature_stock_market.domain.model.IntradayInfo
import com.giovann.composestockmarket.feature_stock_market.domain.repository.StockRepository
import com.giovann.composestockmarket.feature_stock_market.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor (
    private val API: StockAPI,
    private val DB: StockDatabase,
    private val companyListingsParser: CSVParser<CompanyListing>,
    private val intradayInfoParser: CSVParser<IntradayInfo>
) : StockRepository {
    private val DAO: StockDAO = DB.DAO

    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(true))
            val localListings = DAO.searchCompanyListing(query)
            emit(Resource.Success(
                localListings.map { it.toCompanyListing() }
            ))

            val isDBEmpty = localListings.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDBEmpty && !fetchFromRemote
            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteListings = try {
                val response = API.getListings()
                companyListingsParser.parse(response.byteStream())
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(message = "Couldn't load data"))
                null
            }

            remoteListings?.let { listings ->
                DAO.clearCompanyListings()
                DAO.insertCompanyListings(
                    listings.map { it.toCompanyListingEntity() }
                )
                emit(
                    Resource.Success(
                    data = DAO
                        .searchCompanyListing("")
                        .map { it.toCompanyListing() }
                ))
            }

            emit(Resource.Loading(false))
        }
    }

    override suspend fun getIntradayInfo(symbol: String): Resource<List<IntradayInfo>> {
        return try {
            val response = API.getIntradayInfo(
                symbol = symbol
            )
            val results = intradayInfoParser.parse(response.byteStream())
            Resource.Success(results)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(message = "Couldn't load intraday info")
        }
    }

    override suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfo> {
        return try {
            val result = API.getCompanyInfo(
                symbol = symbol
            )
            Resource.Success(result.toCompanyInfo())
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(message = "Couldn't load company info")
        }
    }
}