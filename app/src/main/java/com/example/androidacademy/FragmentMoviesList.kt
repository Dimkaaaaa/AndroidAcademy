package com.example.androidacademy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidacademy.data.JsonMovieRepository
import com.example.androidacademy.databinding.FragmentMoviesListBinding
import com.example.androidacademy.model.Movie
import kotlinx.coroutines.launch

class FragmentMoviesList : Fragment(), OnRecyclerItemClicked {

    private lateinit var movies: List<Movie>
    private lateinit var movieRepository: JsonMovieRepository
    private val adapter = AdapterMovieList(this)
    private lateinit var binding: FragmentMoviesListBinding



    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            movies = getMovies()
            updateAdapter(movies)
        }
        setUpActorListAdapter()
    }

    private suspend fun getMovies(): List<Movie> {
        movieRepository = JsonMovieRepository(requireContext())
        return movieRepository.loadMovies()
    }

    private fun setUpActorListAdapter() {
        binding.recyclerviewMovies.adapter = adapter
        binding.recyclerviewMovies.layoutManager =
                GridLayoutManager(parentFragment?.context, 2, RecyclerView.VERTICAL, false)
    }

    private fun updateAdapter(movies: List<Movie>) {
        adapter.submitList(movies)
    }

    override fun onClick(movie: Movie) {
        parentFragmentManager.beginTransaction()
                .add(R.id.fragment_container, FragmentMoviesDetails.newInstance(movie.id))
                .addToBackStack(null)
                .commit()
    }
}
