package com.giovann.composestockmarket.feature_stock_market.presentation.company_listings

import com.giovann.composestockmarket.feature_stock_market.domain.model.CompanyListing

data class CompanyListingsState(
    val companies: List<CompanyListing> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = ""
)
