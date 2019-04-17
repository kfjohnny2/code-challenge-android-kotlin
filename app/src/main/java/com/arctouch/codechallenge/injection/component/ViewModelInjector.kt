package com.arctouch.codechallenge.injection.component

import com.arctouch.codechallenge.injection.module.NetworkModule
import com.arctouch.codechallenge.ui.details.MovieDetailsViewModel
import com.arctouch.codechallenge.ui.home.HomeViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface ViewModelInjector {

    /**
     * Injects required dependencies into the specified HomeViewModel.
     * @param homeViewModel HomeViewModel in which to inject the dependencies
     */
    fun inject(homeViewModel: HomeViewModel)

    /**
     * Injects required dependencies into the specified MovieDetailsViewModel.
     * @param movieDetailsViewModel MovieDetailsViewModel in which to inject the dependencies
     */
    fun inject(movieDetailsViewModel: MovieDetailsViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder

    }


}