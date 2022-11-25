package com.ecommerceconcept.app_dependencies.repository

import com.ecommerceconcept.home.model.GeoApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOf

class GeoApiImpl : GeoApi {

    private val locationMutableStateFlow = MutableStateFlow<String?>(null)
    override fun getLocationFlow() = locationMutableStateFlow.filterNotNull()


    override fun refreshLocation() {
        locationMutableStateFlow.value = "Zihuatanejo, Gro"
    }
}