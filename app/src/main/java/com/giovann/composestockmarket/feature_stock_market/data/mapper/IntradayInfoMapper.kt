package com.giovann.composestockmarket.feature_stock_market.data.mapper

import com.giovann.composestockmarket.feature_stock_market.data.remote.dto.IntradayInfoDto
import com.giovann.composestockmarket.feature_stock_market.domain.model.IntradayInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun IntradayInfoDto.toIntradayInfo(): IntradayInfo {
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    val localDateTime = LocalDateTime.parse(timestamp, formatter)
    return IntradayInfo(
        date = localDateTime,
        close = close
    )
}