package com.arifwidayana.pokemoncard.common.base

import com.google.gson.annotations.SerializedName

data class BaseResponse<D>(
    @SerializedName("data")
    val data: D,
    @SerializedName("page")
    val page: Int?,
    @SerializedName("pageSize")
    val pageSize: Int?
)