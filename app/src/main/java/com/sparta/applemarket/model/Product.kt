package com.sparta.applemarket.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: Int,
    val image: Int,
    val productName: String,
    val description: String,
    val seller: String,
    val price: Int,
    val address: String,
    var liked: Int,
    val chatting: Int
): Parcelable
