package com.sukowidodo.controlsuara.model

import com.google.gson.annotations.SerializedName

data class Result(
        @SerializedName("name") val name: String,
        @SerializedName("status") val status: Int
)