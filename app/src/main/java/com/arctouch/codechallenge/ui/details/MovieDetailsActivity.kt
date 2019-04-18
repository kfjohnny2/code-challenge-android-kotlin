package com.arctouch.codechallenge.ui.details

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.text.method.ScrollingMovementMethod
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.databinding.MovieDetailsActivityBinding

class MovieDetailsActivity : AppCompatActivity() {
    lateinit var binding: MovieDetailsActivityBinding
    lateinit var viewModel: MovieDetailsViewModel
    private var errorSnackbar: Snackbar? = null


    var movieId: Long = 0

    companion object {
        private const val movieIdExtra = "movieIdExtra"

        fun launch(context: Context, id: Long){
            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra(movieIdExtra, id)
            context.startActivity(intent)

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.movie_details_activity)
        viewModel = ViewModelProviders.of(this).get(MovieDetailsViewModel::class.java)
        movieId = intent.extras!!.getLong(movieIdExtra)

        viewModel.get(movieId)

        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage) else hideError()
        })

        binding.overviewTextView.movementMethod = ScrollingMovementMethod()

        binding.viewModel = viewModel
    }

    private fun showError(@StringRes errorMessage: Int) {
        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.show()
    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finish()
    }
}