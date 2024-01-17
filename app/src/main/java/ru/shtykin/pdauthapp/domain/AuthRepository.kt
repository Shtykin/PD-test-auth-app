package ru.shtykin.pdauthapp.domain

interface AuthRepository {
    suspend fun getCode(login: String): String
    suspend fun getToken(login: String, password: String): String
}