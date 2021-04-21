package com.example.androidacademy.data.retrofit.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class PopularResponse(
    @SerialName("page") val page: Int,
    @SerialName("results") val results: List<MovieResponse>
)
