package com.example.androidacademy

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidacademy.data.JsonMovieRepository
import com.example.androidacademy.databinding.FragmentMoviesDetailsBinding
import com.example.androidacademy.model.Movie
import kotlinx.coroutines.launch

class FragmentMoviesDetails : Fragment() {

    private lateinit var movie: Movie
    private lateinit var movieRepository: JsonMovieRepository
    private lateinit var binding: FragmentMoviesDetailsBinding

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

        binding.ivBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        binding.tvBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        lifecycleScope.launch {
            movieRepository = JsonMovieRepository(requireContext())
            movie = arguments?.let { movieRepository.loadMovie(it.getInt("movieID")) }!!
            bindUI(binding, movie, view)
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
    private fun bindUI(binding: FragmentMoviesDetailsBinding, movie: Movie, view: View) {
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
            val adapter = AdapterActorList()
            adapter.submitList(movie.actors)
            recyclerviewActors.adapter = adapter
            recyclerviewActors.layoutManager =
                    LinearLayoutManager(view.context, RecyclerView.HORIZONTAL, false)
        }
    }
}