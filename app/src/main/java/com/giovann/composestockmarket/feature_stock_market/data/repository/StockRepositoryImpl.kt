package com.giovann.composestockmarket.feature_stock_market.data.repository

import com.giovann.composestockmarket.feature_stock_market.data.csv.CSVParser
import com.giovann.composestockmarket.feature_stock_market.data.local.StockDAO
import com.giovann.composestockmarket.feature_stock_market.data.mapper.toCompanyListing
import com.giovann.composestockmarket.feature_stock_market.data.mapper.toCompanyListingEntity
import com.giovann.composestockmarket.feature_stock_market.data.remote.StockAPI
import com.giovann.composestockmarket.feature_stock_market.domain.model.CompanyListing
import com.giovann.composestockmarket.feature_stock_market.domain.repository.StockRepository
import com.giovann.composestockmarket.feature_stock_market.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class StockRepositoryImpl(
    val API: StockAPI,
    val DAO: StockDAO,
    val companyListingsParser: CSVParser<CompanyListing>
) : StockRepository {

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
}