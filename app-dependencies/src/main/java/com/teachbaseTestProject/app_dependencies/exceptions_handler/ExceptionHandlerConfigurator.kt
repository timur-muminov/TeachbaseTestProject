package com.teachbaseTestProject.app_dependencies.exceptions_handler

import com.teachbaseTestProject.common.exceptions.ClientException
import com.teachbaseTestProject.common.exceptions.ServerException
import com.teachbasetestproject.app_dependencies.R
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