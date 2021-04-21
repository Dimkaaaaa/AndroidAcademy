package com.example.androidacademy.data.room.entitys

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.androidacademy.model.MovieDetails
import com.example.androidacademy.util.Converter

@Entity(tableName = "movie_details")
@TypeConverters(Converter::class)
data class MovieDetailsEntity(
        @PrimaryKey
        val id: Int,
        val adult: Int,
        val backdropPath: String?,
        val genres: List<GenreEntity>,
        val overview: String?,
        val popularity: Int,
        val revenue: Int,
        val runtime: Int?,
        val title: String,
        val voteCount: Int,
        val voteAverage: Double,
        val actors: List<ActorEntity>
)

fun MovieDetailsEntity.asModel(): MovieDetails {

    return MovieDetails(
            id = id,
            pgAge = adult,
            title = title,
            genres = genres.asModel(),
            reviewCount = voteCount,
            isLiked = true,
            rating = voteAverage.toInt(),
            detailImageUrl = backdropPath,
            storyLine = overview.orEmpty(),
            actors = actors.asModel()
    )
}

fun List<MovieDetailsEntity>.asModel(): List<MovieDetails> {

    return this.map {
        MovieDetails(
                id = it.id,
                pgAge = it.adult,
                title = it.title,
                genres = it.genres.asModel(),
                reviewCount = it.voteCount,
                isLiked = true,
                rating = it.voteAverage.toInt(),
                detailImageUrl = it.backdropPath,
                storyLine = it.overview.orEmpty(),
                actors = it.actors.asModel()
        )
    }
}