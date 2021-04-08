package com.example.androidacademy.data

import com.example.androidacademy.model.Movie
import com.example.androidacademy.model.MovieDetails

interface MovieRepository {
    suspend fun loadMovies(): List<Movie>
    suspend fun loadMovie(movieId: Int): MovieDetails
}

class MovieRepositoryImpl(private val remoteDataSource: RemoteDataSource) : MovieRepository {
    override suspend fun loadMovies(): List<Movie> {
        return remoteDataSource.loadMovies()
    }

    override suspend fun loadMovie(movieId: Int): MovieDetails {
        return remoteDataSource.loadMovie(movieId)
    }
}
