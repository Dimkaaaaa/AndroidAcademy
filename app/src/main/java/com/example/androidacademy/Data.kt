package com.example.androidacademy


data class Actor(
    val name: String
) {
    companion object {
        fun getActors(): List<Actor> {
            return listOf(
                Actor("Trys"),
                Actor("Balbes"),
                Actor("Buvaluj")
            )
        }
    }
}

data class Movie(
    val name: String,
    val rating: Int
) {
    companion object {
        fun getMovies(): List<Movie> {
            return listOf(
                Movie("Operacia", 10),
                Movie("bI", 9),
                Movie("I drygie", 8),
                Movie("Prikluchenia", 10),
                Movie("Shyrika", 9)

            )
        }
    }
}
