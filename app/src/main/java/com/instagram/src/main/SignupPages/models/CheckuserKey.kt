package com.instagram.src.main.SignupPages.models

import com.google.gson.annotations.SerializedName

data class CheckuserKey(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result : String
)