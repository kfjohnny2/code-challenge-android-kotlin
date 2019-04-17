package com.arctouch.codechallenge.ui.home

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.databinding.MovieItemBinding
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.ui.home.movie.MovieViewModel
import com.arctouch.codechallenge.util.MovieImageUrlBuilder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.movie_item.view.*

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    private lateinit var movieList: List<Movie>
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): HomeAdapter.ViewHolder {
        val binding: MovieItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(p0.context), R.layout.movie_item, p0, false)

        return ViewHolder(binding)
    }

    override fun getItemCount() = if (::movieList.isInitialized) movieList.size else 0

    fun updatePartnerList(partnerList: List<Movie>) {
        this.movieList = partnerList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(p0: HomeAdapter.ViewHolder, p1: Int) {
        p0.bind(movieList[p1])//To change body of created functions use File | Settings | File Templates.
    }

    class ViewHolder(private val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val viewModel = MovieViewModel()
        private val movieImageUrlBuilder = MovieImageUrlBuilder()

        fun bind(movie: Movie) {
            viewModel.bind(movie)
            Glide.with(itemView)
                    .load(movie.posterPath?.let { movieImageUrlBuilder.buildPosterUrl(it) })
                    .apply(RequestOptions().placeholder(R.drawable.ic_image_placeholder))
                    .into(itemView.posterImageView)

            binding.viewModel = viewModel
        }

    }
}