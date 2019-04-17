package com.arctouch.codechallenge.ui.home

import android.arch.lifecycle.MutableLiveData
import android.view.View
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.base.BaseViewModel
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.model.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class HomeViewModel : BaseViewModel() {
    @Inject
    lateinit var tmdbApi: TmdbApi
    private lateinit var subscription: Disposable
    val movieListAdapter: HomeAdapter = HomeAdapter()

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    val totalPages: MutableLiveData<Int> = MutableLiveData()

    val errorMessage: MutableLiveData<Int> = MutableLiveData()

    init {
        get()
    }

    fun get(page: Long = 1) {
        subscription = tmdbApi.upcomingMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, page, TmdbApi.DEFAULT_REGION)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe{onRetrievePartnerListStart()}
                .doOnTerminate{onRetrievePartnerListFinish()}
                .subscribe(
                        { result ->
                            totalPages.value = result.totalPages
                            val moviesWithGenres = result.results.map { movie ->
                                movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
                            }
                            onRetrievePartnerListSuccess(moviesWithGenres)
                        },
                        { onRetrievePartnerListError() }
                )

    }

    private fun onRetrievePartnerListStart() {
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrievePartnerListFinish() {
        loadingVisibility.value = View.GONE
    }

    private fun onRetrievePartnerListSuccess(movieList: List<Movie>) {
        loadingVisibility.value = View.GONE
        movieListAdapter.updatePartnerList(movieList)
    }

    private fun onRetrievePartnerListError() {
        errorMessage.value = R.string.upcoming_movie_api_error
    }
}