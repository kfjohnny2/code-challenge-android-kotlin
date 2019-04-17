package com.arctouch.codechallenge.ui.home.movie

import android.arch.lifecycle.MutableLiveData
import com.arctouch.codechallenge.base.BaseViewModel
import com.arctouch.codechallenge.model.Movie

class MovieViewModel : BaseViewModel(){
    private val movieTitle = MutableLiveData<String>()
    private val movieGenres = MutableLiveData<String>()
    private val movieReleaseDate = MutableLiveData<String>()

    fun bind(movie: Movie){
        movieTitle.value = movie.title
        movieGenres.value = movie.genres?.joinToString(separator = ", ") { it.name }
        movieReleaseDate.value = movie.releaseDate
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

}