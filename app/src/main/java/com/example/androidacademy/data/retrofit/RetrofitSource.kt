package com.example.androidacademy.data.retrofit

import com.example.androidacademy.data.MovieApiService
import com.example.androidacademy.data.RemoteDataSource
import com.example.androidacademy.data.retrofit.response.ImageResponse
import com.example.androidacademy.model.Actor
import com.example.androidacademy.model.Genre
import com.example.androidacademy.model.Movie
import com.example.androidacademy.model.MovieDetails

class RetrofitSource(private val api: MovieApiService) : RemoteDataSource {

    companion object {
        const val DEFAULT_SIZE = "original"
    }

    private var imageResponse: ImageResponse? = null
    private var baseUrl: String? = null
    private var posterSize: String? = null
    private var backdropSize: String? = null
    private var profileSize: String? = null


    override suspend fun loadMovies(): List<Movie> {
        loadConfiguration()
        val genres = api.loadGenres().genres
        return api.loadPopular(page = 1).results.map { movie ->
            Movie(
                    id = movie.id,
                    pgAge = if (movie.adult) 16 else 13,
                    title = movie.title,
                    genres = genres
                            .filter { genreResponse ->
                                movie.genreIds.contains(genreResponse.id)
                            }.map {
                                Genre(
                                        id = it.id,
                                        name = it.name
                                )
                            },
                    runningTime = 120,
                    reviewCount = movie.voteCount,
                    isLiked = true,
                    rating = movie.voteAverage.toInt(),
                    imageUrl = constructUrl(baseUrl, posterSize, movie.posterPath)
            )
        }
    }

    override suspend fun loadMovie(movieId: Int): MovieDetails {
        loadConfiguration()
        val details = api.loadMovieDetails(movieId)
        return MovieDetails(
                id = details.id,
                pgAge = if (details.adult) 16 else 13,
                title = details.title,
                genres = details.genres.map { Genre(id = it.id, name = it.name) },
                reviewCount = details.voteCount,
                isLiked = true,
                rating = details.voteAverage.toInt(),
                detailImageUrl = constructUrl(baseUrl, backdropSize, details.backdropPath),
                storyLine = details.overview.orEmpty(),
                actors = api.loadMovieCredits(movieId).casts.map {
                    Actor(
                            id = it.id,
                            name = it.name,
                            imageUrl = constructUrl(baseUrl, profileSize, it.profilePath).orEmpty()
                    )
                }
        )
    }

    private suspend fun loadConfiguration() {
        if (imageResponse == null) {
            imageResponse = api.loadConfiguration().images
            baseUrl = imageResponse?.secureBaseUrl
            posterSize = imageResponse?.posterSizes?.find { it == "w500" }
            backdropSize = imageResponse?.backdropSizes?.find { it == "w780" }
            profileSize = imageResponse?.profileSizes?.find { it == "w185" }
        }
    }

    private fun constructUrl(baseUrl: String?, size: String?, path: String?): String? {
        if (path == null || baseUrl == null) return null
        else {
            return baseUrl
                    .plus(size ?: DEFAULT_SIZE)
                    .plus(path)
        }
    }

}