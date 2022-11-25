package com.ecommerceconcept.app_dependencies.exceptions_handler

import com.ecommerceconcept.common.R
import com.ecommerceconcept.common.exceptions.ClientException
import com.ecommerceconcept.common.exceptions.ServerException
import dev.icerock.moko.errors.mappers.ExceptionMappersStorage
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.strResDesc

object ExceptionHandlerConfigurator {

    fun initialize() {
        ExceptionMappersStorage
            .register<ServerException, StringDesc> { R.string.server_exception.strResDesc() }
            .register<ClientException, StringDesc> { R.string.no_internet_connection.strResDesc() }
    }
}