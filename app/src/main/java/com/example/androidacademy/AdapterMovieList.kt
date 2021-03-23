package com.example.androidacademy


import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.androidacademy.databinding.ViewHolderMovieBinding
import com.example.androidacademy.model.Movie

class AdapterMovieList(
        private val clickListener: OnRecyclerItemClicked,
        context: Context,
        var movies: List<Movie>
) : RecyclerView.Adapter<AdapterMovieList.MovieListViewHolder>() {

    private val radius = context.resources.getDimension(R.dimen.corner_radius)
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    private fun getItem(position: Int): Movie = movies[position]

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): AdapterMovieList.MovieListViewHolder {
        val binding =
                ViewHolderMovieBinding.inflate(inflater, parent, false)
        return MovieListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterMovieList.MovieListViewHolder, position: Int) {
        holder.onBind(getItem(position))
        holder.itemView.setOnClickListener {
            clickListener.onClick(getItem(position))
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class MovieListViewHolder(private val binding: ViewHolderMovieBinding) :
            RecyclerView.ViewHolder(binding.root) {

        private val genresList: MutableList<String> = mutableListOf<String>()
        private lateinit var ivStars: List<ImageView>

        @SuppressLint("SetTextI18n")
        fun onBind(movie: Movie) {

            ivStars = listOf(
                    binding.ivStar1,
                    binding.ivStar2,
                    binding.ivStar3,
                    binding.ivStar4,
                    binding.ivStar5,
            )
            genresList.clear()
            Glide.with(binding.root)
                    .load(movie.imageUrl)
                    .transform(RoundedCorners(radius.toInt()))
                    .into(binding.ivMovieList)

            binding.apply {
                tvName.text = movie.title
                movie.genres.listIterator().forEach {
                    genresList.add(it.name)
                }
                tvGenre.text = genresList.joinToString(", ")
                tvAge.text = "${movie.pgAge}+"
                tvReviews.text = "${movie.reviewCount} REVIEWS"
                tvDuration.text = "${movie.runningTime} MIN"
                ivStars.forEachIndexed { index, imageView ->
                    val colorId = if (movie.rating > index) R.color.pink_light else R.color.gray_dark
                    ImageViewCompat.setImageTintList(
                            imageView, ColorStateList.valueOf(
                            ContextCompat.getColor(imageView.context, colorId)
                    )
                    )
                }

            }

        }
    }

}


interface OnRecyclerItemClicked {
    fun onClick(movie: Movie)
}
