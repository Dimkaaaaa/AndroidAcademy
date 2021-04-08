package com.example.androidacademy.movieditails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidacademy.data.MovieRepository
import com.example.androidacademy.model.MovieDetails
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieDetailsViewModel(private val repository: MovieRepository) : ViewModel() {
    private val _movie = MutableStateFlow<MovieDetails?>(null)
    val movie: StateFlow<MovieDetails?> = _movie


    fun loadMovie(movieID: Int) {
        viewModelScope.launch {
            _movie.value = repository.loadMovie(movieID)
        }
    }
}