package com.galangaji.themovielytic.abstraction.util

import android.app.ProgressDialog
import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.galangaji.themovielytic.R
import com.galangaji.themovielytic.viewmodel.FavoriteMovieViewModel

inline fun <reified VM : ViewModel> FragmentActivity.viewModelProvider(
    factory: ViewModelProvider.Factory
) = ViewModelProvider(this, factory)[VM::class.java]

fun Context.showToast(message: String?) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun ImageView.load(url: String) {
    Glide.with(context)
        .load(url)
        .apply(
            RequestOptions.placeholderOf(R.drawable.ic_loading)
                .error(R.drawable.ic_error)
        )
        .into(this)
}