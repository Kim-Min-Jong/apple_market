package com.sparta.applemarket.util

import java.text.DecimalFormat

object Format {
    fun thousandsByComma(price: Int): String {
        val format = DecimalFormat("#,###")
        return format.format(price)
    }
}