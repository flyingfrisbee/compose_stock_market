package com.giovann.composestockmarket.feature_stock_market.data.csv

import android.annotation.SuppressLint
import android.util.Log
import com.giovann.composestockmarket.feature_stock_market.data.mapper.toIntradayInfo
import com.giovann.composestockmarket.feature_stock_market.data.remote.dto.IntradayInfoDto
import com.giovann.composestockmarket.feature_stock_market.domain.model.IntradayInfo
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDate
import java.util.*
import java.util.Calendar.MONDAY
import java.util.Calendar.SUNDAY
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IntradayInfoParser @Inject constructor(
) : CSVParser<IntradayInfo> {

    override suspend fun parse(stream: InputStream): List<IntradayInfo> {
        val daysToSubstract = when (LocalDate.now().dayOfWeek.name) {
            "SUNDAY" -> 2L
            "MONDAY" -> 3L
            else -> 1L
        }
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO) {
            csvReader
                .readAll()
                .drop(1)
                .mapNotNull { line ->
                    val timestamp = line.getOrNull(0) ?: return@mapNotNull null
                    val close = line.getOrNull(4) ?: return@mapNotNull null
                    val dto = IntradayInfoDto(
                        timestamp = timestamp,
                        close = close.toDouble()
                    )
                    dto.toIntradayInfo()
                }
                .filter {
                    it.date.dayOfMonth == LocalDate.now().minusDays(daysToSubstract).dayOfMonth
                }
                .sortedBy {
                    it.date.hour
                }
                .also {
                    csvReader.close()
                }
        }
    }
}