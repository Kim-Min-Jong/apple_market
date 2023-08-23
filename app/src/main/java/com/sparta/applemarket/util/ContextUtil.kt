package com.sparta.applemarket.util

import android.content.Context
import android.widget.Toast

object ContextUtil {
    fun Context.toast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT ).show()
}