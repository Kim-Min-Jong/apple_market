package com.sparta.applemarket.util

import android.content.res.Resources

object FloatUtil {
    fun Float.fromDpToPx() = (this * Resources.getSystem().displayMetrics.density).toInt()
}