package com.example.starwarsapi_paging3_roomdb.util

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.starwarsapi_paging3_roomdb.R
import com.google.android.material.snackbar.Snackbar

object SnackBarExtensions {
    fun View.showNetworkErrorSnackBar(context: Context, message: String) {
        val snackBar = Snackbar.make(
            this,
            message,
            Snackbar.LENGTH_SHORT
        )
            .setAction(context.applicationContext.resources.getString( R.string.ok)) {

            }
            .setActionTextColor(Color.WHITE)

        val snackBarView = snackBar.view
        snackBarView.setBackgroundColor(Color.RED)

        val textView =
            snackBarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setTextColor(Color.WHITE)

        snackBar.show()
    }
}