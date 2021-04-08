package com.example.androidacademy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidacademy.data.MovieRepositoryImpl
import com.example.androidacademy.data.retrofit.RetrofitSource
import com.example.androidacademy.di.NetWork
import com.example.androidacademy.movies.FragmentMoviesList

class MainActivity : AppCompatActivity() {

    private val netWorkModule = NetWork()
    private val remoteDataSource = RetrofitSource(netWorkModule.api)
    val repository = MovieRepositoryImpl(remoteDataSource)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, FragmentMoviesList())
                .commit()

        }
    }
}
