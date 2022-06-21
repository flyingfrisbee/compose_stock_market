package com.giovann.composestockmarket.feature_stock_market.di

import com.giovann.composestockmarket.feature_stock_market.data.csv.CSVParser
import com.giovann.composestockmarket.feature_stock_market.data.csv.CompanyListingsParser
import com.giovann.composestockmarket.feature_stock_market.data.repository.StockRepositoryImpl
import com.giovann.composestockmarket.feature_stock_market.domain.model.CompanyListing
import com.giovann.composestockmarket.feature_stock_market.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCompanyListingsParser(
        companyListingsParser: CompanyListingsParser
    ): CSVParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepositoryImpl: StockRepositoryImpl
    ): StockRepository
}