package com.arctouch.codechallenge.ui.details

import android.arch.lifecycle.MutableLiveData
import android.view.View
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.base.BaseViewModel
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.util.MovieImageUrlBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MovieDetailsViewModel : BaseViewModel(){
    @Inject
    lateinit var tmdbApi: TmdbApi

    private val movieTitle = MutableLiveData<String>()
    private val movieGenres = MutableLiveData<String>()
    private val movieReleaseDate = MutableLiveData<String>()
    private val moviePosterPath = MutableLiveData<String>()
    private val movieBackdropPath = MutableLiveData<String>()
    private val movieOverview = MutableLiveData<String>()
    private val movieImageUrlBuilder = MovieImageUrlBuilder()

    private lateinit var subscription: Disposable

    val errorMessage: MutableLiveData<Int> = MutableLiveData()

    fun get(movieId: Long) {
        subscription = tmdbApi.movie(movieId, TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe{onRetrievePartnerListStart()}
                .doOnTerminate{onRetrievePartnerListFinish()}
                .subscribe(
                        { result -> bind(result)},
                        { onRetrievePartnerListError() }
                )

    }

    fun bind(movie: Movie){
        movieTitle.value = movie.title
        movieGenres.value = movie.genres?.joinToString(separator = ", ") { it.name }
        movieReleaseDate.value = movie.releaseDate
        moviePosterPath.value = movie.posterPath?.let { movieImageUrlBuilder.buildPosterUrl(it) }
        movieBackdropPath.value = movie.backdropPath?.let { movieImageUrlBuilder.buildPosterUrl(it) }
        movieOverview.value = movie.overview
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
    fun getMovieBackdropPath(): MutableLiveData<String> {
        return movieBackdropPath
    }
    fun getMovieOverview(): MutableLiveData<String> {
        return movieOverview
    }

    private fun onRetrievePartnerListStart() {
        errorMessage.value = null
    }

    private fun onRetrievePartnerListFinish() {
    }
    private fun onRetrievePartnerListError() {
        errorMessage.value = R.string.movie_details_api_error
    }
}