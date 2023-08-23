package com.sparta.applemarket.util

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources

object DialogUtil {
    fun showDialog(
        context: Context,
        @DrawableRes iconId: Int,
        @StringRes titleId: Int,
        @StringRes messageId: Int,
        @StringRes positiveButtonText: Int,
        @StringRes negativeButtonText: Int,
        positiveAction: () -> Unit,
        negativeAction: () -> Unit
    ) {
        val builder = AlertDialog.Builder(context)
        builder.setIcon(AppCompatResources.getDrawable(context, iconId))
            .setTitle(context.getString(titleId))
            .setMessage(context.getString(messageId))
            .setCancelable(false)
            .setPositiveButton(positiveButtonText) { _, _ ->
                positiveAction()
            }.setNegativeButton(negativeButtonText) { _, _ ->
                negativeAction()
            }
            .create()
            .show()
    }
}



