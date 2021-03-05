package com.example.androidacademy

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterMovieList(
    private val clickListener: OnRecyclerItemClicked,
    context: Context,
    var movies: List<Movie>
) : RecyclerView.Adapter<AdapterMovieList.MovieListViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    fun getItem(position: Int): Movie = movies[position]

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterMovieList.MovieListViewHolder {
        return MovieListViewHolder(inflater.inflate(R.layout.view_holder_movie, parent, false))
    }

    override fun onBindViewHolder(holder: AdapterMovieList.MovieListViewHolder, position: Int) {
        holder.onBind(getItem(position))
        holder.itemView.setOnClickListener {
            clickListener.onClick()
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    class MovieListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.tv_name)

        fun onBind(movie: Movie) {
            name.text = movie.name
        }

    }

}

interface OnRecyclerItemClicked {
    fun onClick()
}

