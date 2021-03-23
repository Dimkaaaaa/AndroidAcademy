package com.example.androidacademy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.androidacademy.data.JsonMovieRepository
import com.example.androidacademy.model.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class FragmentMoviesDetails: Fragment() {

    private lateinit var movies: List<Movie>
    private val scope = CoroutineScope(Dispatchers.Default + Job())
    private val movieRepository: JsonMovieRepository = JsonMovieRepository(requireContext())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movies_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerview_actors)
////        val actors = Actor.getActors()
////        val adapter = AdapterActorList(view.context, actors)
//        listRecyclerView.adapter = adapter
//        listRecyclerView.layoutManager = LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)

    }
}