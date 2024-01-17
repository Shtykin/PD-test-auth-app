package ru.shtykin.pdauthapp.data.auth_repository

import com.google.gson.Gson
import ru.shtykin.pdauthapp.data.network.ApiService
import ru.shtykin.pdauthapp.data.network.model.ErrorResponse
import ru.shtykin.pdauthapp.data.network.model.ListErrorResponse
import ru.shtykin.pdauthapp.domain.AuthRepository


class AuthRepositoryImpl(
    private val apiService: ApiService,
) : AuthRepository {

    override suspend fun getCode(login: String): String {
        val response = apiService.getCode(login).execute()
        response.body()?.let {
            return it.code
        }
        response.errorBody()?.string()?.let { throw IllegalStateException(parseError(it)) }
        throw IllegalStateException("Response body is empty")
    }

    override suspend fun getToken(login: String, password: String): String {
        val response = apiService.getToken(login, password).execute()
        response.body()?.let { return it }
        response.errorBody()?.string()?.let { throw IllegalStateException(parseError(it)) }
        throw IllegalStateException("Response body is empty")
    }


    private fun parseError(errorMessage: String): String? {
        val gson = Gson()
        var message: String? = try {
            val listError = gson.fromJson(errorMessage, ListErrorResponse::class.java)
            listError.error.firstOrNull()
        } catch (e: Exception) {
            null
        }
        if (message != null) return message

        message = try {
            val error = gson.fromJson(errorMessage, ErrorResponse::class.java)
            error.error
        } catch (e: Exception) {
            null
        }
        return message

    }

}