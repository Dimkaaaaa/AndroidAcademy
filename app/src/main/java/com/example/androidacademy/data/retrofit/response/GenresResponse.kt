package com.example.androidacademy.data.retrofit.response

import com.example.androidacademy.data.room.entitys.GenreEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class GenresResponse(
        @SerialName("genres") val genres: List<GenreResponse>
)

fun GenresResponse.asDatabaseModel(): List<GenreEntity> {
    return genres.map {
        GenreEntity(
                genreId = it.id,
                name = it.name
        )
    }
}