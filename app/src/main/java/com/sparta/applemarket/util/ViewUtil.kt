package com.sparta.applemarket.util

import android.view.View
import com.google.android.material.snackbar.Snackbar

object ViewUtil {
    fun View.appearSnackBar(message: String) =
        Snackbar.make(this, message, 2000).show()
}