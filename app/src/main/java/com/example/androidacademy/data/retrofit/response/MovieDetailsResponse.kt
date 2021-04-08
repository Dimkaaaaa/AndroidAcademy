package com.example.androidacademy.data.retrofit.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class MovieDetailsResponse(
        @SerialName("adult") val adult : Boolean,
        @SerialName("backdrop_path") val backdropPath : String?,
        @SerialName("genres") val genres : List<GenreResponse>,
        @SerialName("id") val id : Int,
        @SerialName("overview") val overview : String?,
        @SerialName("popularity") val popularity : Double,
        @SerialName("revenue") val revenue : Int,
        @SerialName("runtime") val runtime : Int?,
        @SerialName("title") val title : String,
        @SerialName("vote_count") val voteCount : Int,
        @SerialName("vote_average") val voteAverage : Double
)
