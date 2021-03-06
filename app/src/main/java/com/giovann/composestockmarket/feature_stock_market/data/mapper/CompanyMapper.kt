package com.giovann.composestockmarket.feature_stock_market.data.mapper

import com.giovann.composestockmarket.feature_stock_market.data.local.CompanyListingEntity
import com.giovann.composestockmarket.feature_stock_market.data.remote.dto.CompanyInfoDto
import com.giovann.composestockmarket.feature_stock_market.domain.model.CompanyInfo
import com.giovann.composestockmarket.feature_stock_market.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing(): CompanyListing {
    return CompanyListing(
        name = name,
        symbol = symbol,
        exchange = exchange,
    )
}

fun CompanyListing.toCompanyListingEntity(): CompanyListingEntity {
    return CompanyListingEntity(
        name = name,
        symbol = symbol,
        exchange = exchange,
    )
}

fun CompanyInfoDto.toCompanyInfo(): CompanyInfo {
    return CompanyInfo(
        symbol = symbol ?: "",
        description = description ?: "",
        name = name ?: "",
        country = country ?: "",
        industry = industry ?: "",
    )
}