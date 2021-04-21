package com.example.androidacademy.data.room.entitys

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androidacademy.model.Genre

@Entity(tableName = "genres")
data class GenreEntity(
    @PrimaryKey
    val genreId: Int,
    val name: String
)

fun List<GenreEntity>.asModel(): List<Genre> {

    return this.map {
        Genre(
            id = it.genreId,
            name = it.name
        )
    }
}