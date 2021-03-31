package com.example.androidacademy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidacademy.data.JsonMovieRepository
import com.example.androidacademy.data.MovieRepository
import com.example.androidacademy.movies.FragmentMoviesList

class MainActivity : AppCompatActivity() {

    lateinit var repository: MovieRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        repository = JsonMovieRepository(applicationContext)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, FragmentMoviesList())
                .commit()

        }
    }
}
