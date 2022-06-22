package com.giovann.composestockmarket.feature_stock_market.presentation.company_info

import com.giovann.composestockmarket.feature_stock_market.domain.model.CompanyInfo
import com.giovann.composestockmarket.feature_stock_market.domain.model.IntradayInfo

data class CompanyInfoState(
    val stockInfos: List<IntradayInfo> = emptyList(),
    val company: CompanyInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)