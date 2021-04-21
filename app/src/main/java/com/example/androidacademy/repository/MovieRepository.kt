package com.example.androidacademy.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.androidacademy.data.retrofit.RetrofitSource
import com.example.androidacademy.data.room.entitys.asModel
import com.example.androidacademy.di.NetWork
import com.example.androidacademy.di.RoomSource
import com.example.androidacademy.model.Movie
import com.example.androidacademy.model.MovieDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepository(private val database: RoomSource) {

    val movies: LiveData<List<Movie>> = Transformations.map(database.dao.getAllMovies()) {
        it.asModel()
    }

    var movieDetails: MovieDetails? = null

    suspend fun refreshMovies() {
        withContext(Dispatchers.IO) {
            try {
                val movies = RetrofitSource(NetWork.api).loadMoviesFromNet()
                database.dao.insertAllMovies(movies)
            } catch (e: Exception) {
                // Описать действия при отсутствии интернета
            }
        }
    }

    suspend fun loadMovieFromDb(movieId: Int) {
        withContext(Dispatchers.IO) {
            movieDetails = database.dao.getMovieDetails(movieId)?.asModel()
        }
    }

    suspend fun refreshMovie(movieId: Int) {
        withContext(Dispatchers.IO) {
            try {
                val movieD = RetrofitSource(NetWork.api).loadMovieFromNet(movieId)
                database.dao.insertMovieDetails(movieD)
                movieDetails = movieD.asModel()
            } catch (e: Exception) {
            }
        }

    }
}