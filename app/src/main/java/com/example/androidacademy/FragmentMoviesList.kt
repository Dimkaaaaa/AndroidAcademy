package com.example.androidacademy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidacademy.data.JsonMovieRepository
import com.example.androidacademy.model.Movie
import kotlinx.coroutines.*

class FragmentMoviesList : Fragment() {


    private lateinit var movies: List<Movie>
    private val scope = CoroutineScope(Dispatchers.Default + Job())
    private lateinit var movieRepository: JsonMovieRepository
    private lateinit var adapter: AdapterMovieList
    private lateinit var listRecyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_movies_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        scope.launch {
            movieRepository = JsonMovieRepository(requireContext())
            movies = movieRepository.loadMovies()
            listRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerview_movies)
            adapter = AdapterMovieList(clickListener, view.context, movies)
            withContext(Dispatchers.Main) {
                listRecyclerView.adapter = adapter
                listRecyclerView.layoutManager =
                    GridLayoutManager(view.context, 2, RecyclerView.VERTICAL, false)
            }
        }

    }

    private val clickListener = object : OnRecyclerItemClicked {
        override fun onClick(movie: Movie) {
            parentFragmentManager.beginTransaction()
                .add(R.id.fragment_container, FragmentMoviesDetails.newInstance(movie.id))
                .addToBackStack(null)
                .commit()
        }
    }
}