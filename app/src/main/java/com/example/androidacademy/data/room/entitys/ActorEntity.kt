package com.example.androidacademy.data.room.entitys


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androidacademy.model.Actor

@Entity(tableName = "actors")
data class ActorEntity(
        @PrimaryKey val id: Int,
        val name: String,
        val profilePath: String?
)

fun List<ActorEntity>.asModel(): List<Actor> {

    return this.map {
        Actor(
                id = it.id,
                name = it.name,
                imageUrl = it.profilePath
        )
    }
}

