package com.example.androidacademy.data


import com.example.androidacademy.data.room.entitys.MovieDetailsEntity
import com.example.androidacademy.data.room.entitys.MovieEntity

interface RemoteDataSource {
    suspend fun loadMoviesFromNet(): List<MovieEntity>
    suspend fun loadMovieFromNet(movieId: Int): MovieDetailsEntity
}