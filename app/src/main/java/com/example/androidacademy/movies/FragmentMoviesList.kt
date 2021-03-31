package com.example.androidacademy.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidacademy.AdapterMovieList
import com.example.androidacademy.MainActivity
import com.example.androidacademy.OnRecyclerItemClicked
import com.example.androidacademy.R
import com.example.androidacademy.databinding.FragmentMoviesListBinding
import com.example.androidacademy.model.Movie
import com.example.androidacademy.movieditails.FragmentMoviesDetails
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FragmentMoviesList : Fragment(), OnRecyclerItemClicked {


    private val adapter = AdapterMovieList(this)
    private lateinit var binding: FragmentMoviesListBinding

    private lateinit var viewModel: MovieListViewModel

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

        viewModel = ViewModelProvider(this, MovieListViewModelFactory((requireActivity() as MainActivity).repository)).get(MovieListViewModel::class.java)

        setUpActorListAdapter()
        loadDataToAdapter(adapter)
    }

    private fun loadDataToAdapter(adapter: AdapterMovieList) {
        lifecycleScope.launch {
            viewModel.movies.collect { movieList ->
                adapter.submitList(movieList)
            }
        }
    }


    private fun setUpActorListAdapter() {
        binding.recyclerviewMovies.adapter = adapter
        binding.recyclerviewMovies.layoutManager =
                GridLayoutManager(parentFragment?.context, 2, RecyclerView.VERTICAL, false)
    }


    override fun onClick(movie: Movie) {
        parentFragmentManager.beginTransaction()
                .add(R.id.fragment_container, FragmentMoviesDetails.newInstance(movie.id))
                .addToBackStack(null)
                .commit()
    }

}
