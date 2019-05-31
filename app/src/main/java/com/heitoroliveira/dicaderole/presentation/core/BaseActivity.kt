package com.heitoroliveira.dicaderole.presentation.core

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.transition.Explode
import android.view.Window
import com.heitoroliveira.dicaderole.R

abstract class BaseActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(window) {
            requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)

            // set an exit transition
            exitTransition = Explode()
        }
    }

    fun processError(viewError: ViewError?) {
        when(viewError){
            is ViewError.ErrorWithStringResourceMessage -> {
                Snackbar.make(findViewById(android.R.id.content), viewError.resId, Snackbar.LENGTH_SHORT).show()
            }
            is ViewError.ErrorOnLoadData -> {
                Snackbar.make(window.decorView, R.string.was_not_possible_to_load_data, Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}