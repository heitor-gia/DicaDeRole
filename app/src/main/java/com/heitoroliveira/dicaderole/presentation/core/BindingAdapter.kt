package com.heitoroliveira.dicaderole.presentation.core

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.heitoroliveira.dicaderole.R
import com.squareup.picasso.Picasso


@BindingAdapter("app:srcUrl")
fun bindRemoteImage(imageView: ImageView, url: String?) {
    Picasso.get()
        .load(url)
        .fit()
        .centerCrop()
        .placeholder(R.drawable.image_placeholder)
        .into(imageView)
}



