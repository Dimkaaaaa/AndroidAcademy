package com.example.androidacademy.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidacademy.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieListViewModel(private val repository: MovieRepository): ViewModel() {

    val movies = repository.movies

    init {
        refreshMovies()
    }

    private fun refreshMovies() {
        viewModelScope.launch {
            repository.refreshMovies()
        }
    }

}