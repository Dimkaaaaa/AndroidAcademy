package com.example.androidacademy.movieditails

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidacademy.AdapterActorList
import com.example.androidacademy.MainActivity
import com.example.androidacademy.R
import com.example.androidacademy.data.JsonMovieRepository
import com.example.androidacademy.databinding.FragmentMoviesDetailsBinding
import com.example.androidacademy.model.Actor
import com.example.androidacademy.model.Movie
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FragmentMoviesDetails : Fragment() {

    private lateinit var movieRepository: JsonMovieRepository
    private lateinit var binding: FragmentMoviesDetailsBinding
    private val adapter = AdapterActorList()
    private lateinit var viewModel: MovieDetailsViewModel
    private var movieID: Int = 0

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, MovieDetailsViewModelFactory((requireActivity() as MainActivity).repository)).get(MovieDetailsViewModel::class.java)
        movieID = arguments?.getInt("movieID")!!

        setUpActorListAdapter()
        setUpListeners()

        viewModel.loadMovie(movieID)

        lifecycleScope.launch {

            viewModel.movie.collect {
                it?.let {
                    bindUI(it)
                    updateAdapter(it.actors)
                }
            }
        }


    }


    private suspend fun getMovie(): Movie {
        movieRepository = JsonMovieRepository(requireContext())
        return arguments?.let { movieRepository.loadMovie(it.getInt("movieID")) }!!
    }

    private fun setUpListeners() {
        binding.ivBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        binding.tvBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }


    companion object {
        fun newInstance(movieID: Int): FragmentMoviesDetails {
            val args = Bundle()
            args.putInt("movieID", movieID)
            val fragment = FragmentMoviesDetails()
            fragment.arguments = args
            return fragment
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindUI(movie: Movie) {
        val genresList: MutableList<String> = mutableListOf()
        val ivStars = listOf(
                binding.ivStar1,
                binding.ivStar2,
                binding.ivStar3,
                binding.ivStar4,
                binding.ivStar5,
        )

        Glide.with(binding.root)
                .load(movie.imageUrl)
                .into(binding.ivPosterPicture)

        binding.apply {
            tvName.text = movie.title
            tvPgAge.text = "${movie.pgAge}+"
            movie.genres.listIterator().forEach {
                genresList.add(it.name)
            }
            tvGenre.text = genresList.joinToString(", ")
            tvReviews.text = "${movie.reviewCount} REVIEWS"
            tvStory.text = movie.storyLine
            ivStars.forEachIndexed { index, imageView ->
                val colorId = if (movie.rating > index) R.color.pink_light else R.color.gray_dark
                ImageViewCompat.setImageTintList(
                        imageView, ColorStateList.valueOf(
                        ContextCompat.getColor(imageView.context, colorId)))
            }

        }
    }

    private fun setUpActorListAdapter() {
        binding.recyclerviewActors.adapter = adapter
        binding.recyclerviewActors.layoutManager =
                LinearLayoutManager(parentFragment?.context, RecyclerView.HORIZONTAL, false)
    }

    private fun updateAdapter(actors: List<Actor>) {
        adapter.submitList(actors)
    }
}


