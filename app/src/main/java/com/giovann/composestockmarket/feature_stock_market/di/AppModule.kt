package com.giovann.composestockmarket.feature_stock_market.di

import android.content.Context
import androidx.room.Room
import com.giovann.composestockmarket.feature_stock_market.data.local.StockDatabase
import com.giovann.composestockmarket.feature_stock_market.data.local.StockDatabase.Companion.DATABASE_NAME
import com.giovann.composestockmarket.feature_stock_market.data.remote.StockAPI
import com.giovann.composestockmarket.feature_stock_market.data.repository.StockRepositoryImpl
import com.giovann.composestockmarket.feature_stock_market.domain.repository.StockRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideStockAPI(): StockAPI {
        return Retrofit
            .Builder()
            .baseUrl(StockAPI.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(StockAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideStockDatabase(
        @ApplicationContext context: Context
    ): StockDatabase {
        return Room.databaseBuilder(
            context,
            StockDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
}