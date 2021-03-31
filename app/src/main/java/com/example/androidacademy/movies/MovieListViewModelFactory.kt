package com.example.androidacademy.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidacademy.data.MovieRepository

class MovieListViewModelFactory (private val repository: MovieRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieListViewModel::class.java)) {
            return MovieListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}