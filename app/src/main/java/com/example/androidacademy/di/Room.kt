package com.example.androidacademy.di

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.androidacademy.data.room.DAO
import com.example.androidacademy.data.room.entitys.ActorEntity
import com.example.androidacademy.data.room.entitys.GenreEntity
import com.example.androidacademy.data.room.entitys.MovieDetailsEntity
import com.example.androidacademy.data.room.entitys.MovieEntity

@Database(entities = [MovieEntity::class, GenreEntity::class, MovieDetailsEntity::class, ActorEntity::class], version = 1, exportSchema = false)
abstract class RoomSource : RoomDatabase() {
    abstract val dao: DAO
}

private lateinit var INSTANCE: RoomSource

fun getDatabase(context: Context): RoomSource {
    synchronized(RoomSource::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                    RoomSource::class.java,
                    "Movies").build()
        }
    }
    return INSTANCE
}