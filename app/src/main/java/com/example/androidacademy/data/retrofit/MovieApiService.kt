package com.example.androidacademy.data.retrofit

import com.example.androidacademy.data.retrofit.response.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    /**
     * method for receiving app configurations, like base image URLs. You need this method for downloading images for films.
     */
    @GET("configuration")
    suspend fun loadConfiguration(): ConfigurationResponse

    /**
     * Get the list of official genres for movies.
     */
    @GET("genre/movie/list")
    suspend fun loadGenres(): GenresResponse

    /**
     * use one of these methods for receiving films. You can use all of these if you want to show different categories of films.
     */
    @GET("movie/popular")
    suspend fun loadPopular(
        @Query("page") page: Int
    ): PopularResponse
    /**
     * for receiving movie details information.
     */
    @GET("movie/{movie_id}")
    suspend fun loadMovieDetails(
        @Path("movie_id") movieId: Int
    ): MovieDetailsResponse

    /**
     * Get the cast and crew for a movie.
     */
    @GET("movie/{movie_id}/credits")
    suspend fun loadMovieCredits(
        @Path("movie_id") movieId: Int
    ): MovieCastResponse
}