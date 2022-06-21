package com.giovann.composestockmarket.feature_stock_market.domain.model

import java.time.LocalDateTime

data class IntradayInfo(
    val date: LocalDateTime,
    val close: Double
)