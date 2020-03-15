package com.mobiledevpro.local.mapper

import com.mobiledevpro.data.model.CountryEntity
import com.mobiledevpro.data.model.TotalEntity
import com.mobiledevpro.local.database.model.CachedCounties
import com.mobiledevpro.local.database.model.CachedTotal

fun CachedTotal.toEntity() = TotalEntity(
    confirmed = confirmed,
    deaths = deaths,
    recovered = recovered,
    lastUpdateTime = lastUpdateTime
)

fun TotalEntity.toCached() = CachedTotal(
    confirmed = confirmed,
    deaths = deaths,
    recovered = recovered,
    lastUpdateTime = lastUpdateTime
)

fun CachedCounties.toEntity() = CountryEntity(
    id = id,
    country = country,
    updated = updated,
    latitude = latitude,
    longitude = longitude,
    confirmed = confirmed,
    deaths = deaths,
    recovered = recovered,
    active = active
)

fun CountryEntity.toCached() = CachedCounties(
    id = id,
    country = country,
    updated = updated,
    latitude = latitude,
    longitude = longitude,
    confirmed = confirmed,
    deaths = deaths,
    recovered = recovered,
    active = active
)