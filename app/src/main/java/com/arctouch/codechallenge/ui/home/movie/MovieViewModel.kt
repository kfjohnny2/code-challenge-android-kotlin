package com.arctouch.codechallenge.ui.home.movie

import android.arch.lifecycle.MutableLiveData
import com.arctouch.codechallenge.base.BaseViewModel
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.util.MovieImageUrlBuilder

class MovieViewModel : BaseViewModel(){
    private val movieTitle = MutableLiveData<String>()
    private val movieGenres = MutableLiveData<String>()
    private val movieReleaseDate = MutableLiveData<String>()
    private val moviePosterPath = MutableLiveData<String>()
    private val movieImageUrlBuilder = MovieImageUrlBuilder()

    fun bind(movie: Movie){
        movieTitle.value = movie.title
        movieGenres.value = movie.genres?.joinToString(separator = ", ") { it.name }
        movieReleaseDate.value = movie.releaseDate
        moviePosterPath.value = movie.posterPath?.let { movieImageUrlBuilder.buildPosterUrl(it) }
    }

    fun getMovieTitle(): MutableLiveData<String> {
        return movieTitle
    }

    fun getMovieGenres(): MutableLiveData<String> {
        return movieGenres
    }

    fun getMovieReleaseDate(): MutableLiveData<String> {
        return movieReleaseDate
    }

    fun getMoviePosterPath(): MutableLiveData<String> {
        return moviePosterPath
    }
}