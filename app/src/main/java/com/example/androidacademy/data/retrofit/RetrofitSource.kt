package com.example.androidacademy.data.retrofit

import com.example.androidacademy.data.RemoteDataSource
import com.example.androidacademy.data.retrofit.response.ImageResponse
import com.example.androidacademy.data.retrofit.response.asDatabase
import com.example.androidacademy.data.retrofit.response.asDatabaseModel
import com.example.androidacademy.data.room.entitys.ActorEntity
import com.example.androidacademy.data.room.entitys.MovieDetailsEntity
import com.example.androidacademy.data.room.entitys.MovieEntity

class RetrofitSource(private val api: MovieApiService) : RemoteDataSource {
    companion object {
        const val DEFAULT_SIZE = "original"
    }

    private var imageResponse: ImageResponse? = null
    private var baseUrl: String? = null
    private var posterSize: String? = null
    private var backdropSize: String? = null
    private var profileSize: String? = null

    override suspend fun loadMoviesFromNet(): List<MovieEntity> {
        loadConfiguration()
        val genresList = api.loadGenres().asDatabaseModel()
        return api.loadPopular(page = 1).results.map { movie ->
            MovieEntity(
                    id = movie.id,
                    title = movie.title,
                    posterPath = formingUrl(baseUrl, posterSize, movie.posterPath),
                    voteAverage = movie.voteAverage.toInt(),
                    voteCount = movie.voteCount,
                    adult = if (movie.adult) 16 else 13,
                    genreIds = genresList.filter { genreEntity ->
                        movie.genreIds.contains(genreEntity.genreId)
                    }
            )
        }

    }


    override suspend fun loadMovieFromNet(movieId: Int): MovieDetailsEntity {
        loadConfiguration()
        val details = api.loadMovieDetails(movieId)
        return MovieDetailsEntity(
                id = details.id,
                adult = if (details.adult) 16 else 13,
                title = details.title,
                genres = details.genres.asDatabase(),
                revenue = details.revenue,
                voteCount = details.popularity.toInt(),
                backdropPath = formingUrl(baseUrl, backdropSize, details.backdropPath),
                overview = details.overview.orEmpty(),
                popularity = details.voteCount,
                runtime = details.runtime,
                voteAverage = details.voteAverage,
                actors = api.loadMovieCredits(movieId).casts.map {
                    ActorEntity(
                            id = it.id,
                            name = it.name,
                            profilePath = formingUrl(baseUrl, profileSize, it.profilePath)
                    )
                }
        )
    }

    private suspend fun loadConfiguration() {
        if (imageResponse == null) {
            imageResponse = api.loadConfiguration().images
            baseUrl = imageResponse?.secureBaseUrl
            // TODO придумать более изящный вариант
            posterSize = imageResponse?.posterSizes?.find { it == "w500" }
            // TODO придумать более изящный вариант
            backdropSize = imageResponse?.backdropSizes?.find { it == "w780" }
            // TODO придумать более изящный вариант
            profileSize = imageResponse?.profileSizes?.find { it == "w185" }
        }
    }

    private fun formingUrl(url: String?, size: String?, path: String?): String? {
        return if (url == null || path == null) {
            null
        } else {
            url.plus(size.takeUnless { it.isNullOrEmpty() } ?: DEFAULT_SIZE)
                    .plus(path)
        }
    }
}