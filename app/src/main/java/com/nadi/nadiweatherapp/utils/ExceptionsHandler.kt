package com.nadi.nadiweatherapp.utils

import android.content.Context
import android.widget.Toast
import java.net.ConnectException
import java.net.UnknownHostException

//here we are handling exception
fun exceptionsHandler(context: Context, exception: Exception) {
    when (exception) {
        is RuntimeException -> Toast.makeText(
            context,
            exception.message.toString(),
            Toast.LENGTH_SHORT
        ).show()

        is ConnectException, is UnknownHostException -> Toast.makeText(
            context,
            "Internet connection error, try again later",
            Toast.LENGTH_SHORT
        ).show()

        else -> Toast.makeText(context, "An error occurred, try again later", Toast.LENGTH_SHORT)
            .show()
    }
}


