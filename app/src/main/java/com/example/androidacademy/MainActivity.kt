package com.example.androidacademy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidacademy.di.getDatabase

import com.example.androidacademy.movies.FragmentMoviesList
import com.example.androidacademy.repository.MovieRepository

class MainActivity : AppCompatActivity() {


    lateinit var repository: MovieRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        repository = MovieRepository(getDatabase(applicationContext))
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, FragmentMoviesList())
                .commit()
        }
    }
}
