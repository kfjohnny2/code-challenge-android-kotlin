package com.arctouch.codechallenge.base

import android.arch.lifecycle.ViewModel
import com.arctouch.codechallenge.injection.component.DaggerViewModelInjector
import com.arctouch.codechallenge.injection.component.ViewModelInjector
import com.arctouch.codechallenge.injection.module.NetworkModule
import com.arctouch.codechallenge.ui.home.HomeViewModel

abstract class BaseViewModel: ViewModel(){
    private val injectorApi: ViewModelInjector = DaggerViewModelInjector
            .builder()
            .networkModule(NetworkModule)
            .build()


    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when(this){
            is HomeViewModel -> injectorApi.inject(this)
        }
    }

}