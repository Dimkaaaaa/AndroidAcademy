package com.example.androidacademy.movieditails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidacademy.model.MovieDetails
import com.example.androidacademy.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieDetailsViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _movie = MutableLiveData<MovieDetails?>(null)
    val movie: MutableLiveData<MovieDetails?> = _movie

    fun loadMovie(movieID: Int) {
        viewModelScope.launch {
            repository.loadMovieFromDb(movieID)
            _movie.value = repository.movieDetails
            repository.refreshMovie(movieID)
            _movie.value = repository.movieDetails
        }
    }

}