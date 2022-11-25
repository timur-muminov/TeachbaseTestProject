package com.ecommerceconcept.home.model

import kotlinx.coroutines.flow.Flow

interface GeoApi {
    fun getLocationFlow(): Flow<String>
    fun refreshLocation()
}