package com.example.androidacademy.data


import com.example.androidacademy.model.Movie
import com.example.androidacademy.model.MovieDetails

interface RemoteDataSource {
    suspend fun loadMovies(): List<Movie>
    suspend fun loadMovie(movieId: Int): MovieDetails
}