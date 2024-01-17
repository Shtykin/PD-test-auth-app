package ru.shtykin.pdauthapp.data.network.model

import com.google.gson.annotations.SerializedName

data class ResponseCodeDto(
    @SerializedName("code") val code: String,
    @SerializedName("status") val status: String,
)