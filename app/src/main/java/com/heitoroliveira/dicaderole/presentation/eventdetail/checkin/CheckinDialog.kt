package com.heitoroliveira.dicaderole.presentation.eventdetail.checkin

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.heitoroliveira.dicaderole.R
import com.heitoroliveira.dicaderole.presentation.core.ViewError

class CheckinDialog(
    context: Context?,
    val onConfirm: (String, String) -> Unit = { _, _ -> }
) : AlertDialog(context) {
    private val etName: EditText
    private val etEmail: EditText
    private val loadingLayout: View
    private val tvError: TextView

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_checkin, null, false)
        etName = view.findViewById(R.id.etName)
        etEmail = view.findViewById(R.id.etEmail)
        loadingLayout = view.findViewById(R.id.loadingLayout)
        tvError = view.findViewById(R.id.tvError)
        view.findViewById<Button>(R.id.btnConfirm).setOnClickListener {
            val name = etName.text.toString()
            val email = etEmail.text.toString()
            onConfirm(name, email)
        }
        setView(view)
    }

    fun setCheckinStatus(checkinStatus: CheckinStatus) {
        if (checkinStatus !is CheckinStatus.Loading){
            loadingLayout.visibility = View.GONE
            setCancelable(true)
        }
        if (checkinStatus !is CheckinStatus.Error) tvError.visibility = View.GONE

        when (checkinStatus) {
            is CheckinStatus.Completed, CheckinStatus.Idle -> dismiss()
            is CheckinStatus.Loading -> {
                loadingLayout.visibility = View.VISIBLE
                setCancelable(false)
            }
            is CheckinStatus.Error -> tvError.apply {
                visibility = View.VISIBLE
                when (checkinStatus.viewError) {
                    is ViewError.ErrorWithStringResourceMessage -> setText(checkinStatus.viewError.resId)
                    else -> setText(R.string.was_not_possible_to_load_data)
                }
            }
        }
    }

}