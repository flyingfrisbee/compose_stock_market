package com.giovann.composestockmarket.feature_stock_market.data.repository

import com.giovann.composestockmarket.feature_stock_market.data.local.StockDAO
import com.giovann.composestockmarket.feature_stock_market.data.local.StockDatabase
import com.giovann.composestockmarket.feature_stock_market.data.mapper.toCompanyListing
import com.giovann.composestockmarket.feature_stock_market.data.remote.StockAPI
import com.giovann.composestockmarket.feature_stock_market.domain.model.CompanyListing
import com.giovann.composestockmarket.feature_stock_market.domain.repository.StockRepository
import com.giovann.composestockmarket.feature_stock_market.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class StockRepositoryImpl @Inject constructor(
    val API: StockAPI,
    val DAO: StockDAO
) : StockRepository {

    override suspend fun getListings(
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
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
            }
        }
    }
}