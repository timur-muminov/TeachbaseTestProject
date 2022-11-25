package com.ecommerceconcept.app_dependencies.exceptions_handler

import com.ecommerceconcept.common.exceptions.ClientException
import com.ecommerceconcept.common.exceptions.ServerException
import retrofit2.Response

class NetworkExceptionMapper {

    suspend fun <DATA> handle(request: suspend () -> Response<DATA>): DATA {
        try {
            val response = request.invoke()
            val body = response.body()
            if (!response.isSuccessful || body == null) throw ServerException(response.message())
            return body
        } catch (ex: Exception) {
            throw when (ex) {
                is ServerException -> ex
                else -> ClientException(ex)
            }
        }
    }
}