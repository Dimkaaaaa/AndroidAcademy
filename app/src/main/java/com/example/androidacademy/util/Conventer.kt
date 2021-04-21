package com.example.androidacademy.util

import androidx.room.TypeConverter
import com.example.androidacademy.data.room.entitys.ActorEntity
import com.example.androidacademy.data.room.entitys.GenreEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {
    @TypeConverter
    fun fromInteger(hobbies: List<Int>): String {
        return hobbies.joinToString(",")
    }

    @TypeConverter
    fun toInteger(data: String): List<Int> {
        return data.split(",").map { it.trim().toInt() }
    }


    @TypeConverter
    fun fromGenresList(genres: List<GenreEntity>): String {
        val type = object : TypeToken<List<GenreEntity>>() {}.type
        return Gson().toJson(genres, type)
    }

    @TypeConverter
    fun toGenresList(genresString: String): List<GenreEntity> {
        val type = object : TypeToken<List<GenreEntity>>() {}.type
        return Gson().fromJson<List<GenreEntity>>(genresString, type)
    }


    @TypeConverter
    fun fromActorsList(actors: List<ActorEntity>): String {
        val type = object : TypeToken<List<ActorEntity>>() {}.type
        return Gson().toJson(actors, type)
    }

    @TypeConverter
    fun toActorsList(actorsString: String): List<ActorEntity> {
        val type = object : TypeToken<List<ActorEntity>>() {}.type
        return Gson().fromJson<List<ActorEntity>>(actorsString, type)
    }

}