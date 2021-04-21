package com.example.androidacademy.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.androidacademy.data.room.entitys.MovieDetailsEntity
import com.example.androidacademy.data.room.entitys.MovieEntity

@Dao
interface DAO {

    @Query("SELECT * FROM movies")
    fun getAllMovies(): LiveData<List<MovieEntity>>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMovies(films: List<MovieEntity>)

    @Query("SELECT * FROM movie_details WHERE id = :movieId")
    fun getMovieDetails(movieId: Int): MovieDetailsEntity?


    @Query("SELECT * FROM movie_details")
    fun getAllMovieDetails(): LiveData<List<MovieDetailsEntity>>


    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieDetails(movieDetails: MovieDetailsEntity)

}