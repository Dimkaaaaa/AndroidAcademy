package com.example.androidacademy.data.room.entitys

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.androidacademy.model.Movie
import com.example.androidacademy.util.Converter

@Entity(tableName = "movies")
@TypeConverters(Converter::class)
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val posterPath: String?,
    val adult: Int,
    val genreIds: List<GenreEntity>,
    val title: String,
    val voteCount: Int,
    val voteAverage: Int
)

fun List<MovieEntity>.asModel(): List<Movie>{

    return this.map {
        Movie(
            id = it.id,
            pgAge = it.adult,
            title = it.title,
            genres = it.genreIds.asModel(),
            runningTime = 120,
            reviewCount = it.voteCount,
            isLiked = true,
            rating = it.voteAverage,
            imageUrl = it.posterPath
        )
    }
}
