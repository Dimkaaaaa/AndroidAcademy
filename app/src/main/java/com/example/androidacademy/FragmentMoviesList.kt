package com.example.androidacademy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FragmentMoviesList : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movies_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerview_movies)
        val movies = Movie.getMovies()
        val adapter = AdapterMovieList(clickListener,view.context, movies)
        listRecyclerView.adapter = adapter
        listRecyclerView.layoutManager = GridLayoutManager(view.context, 2, RecyclerView.VERTICAL, false)

    }

    private val clickListener = object:OnRecyclerItemClicked{
        override fun onClick() {
            parentFragmentManager.beginTransaction()
                .add(R.id.fragment_container, FragmentMoviesDetails())
                .addToBackStack(null)
                .commit()
        }
    }
}