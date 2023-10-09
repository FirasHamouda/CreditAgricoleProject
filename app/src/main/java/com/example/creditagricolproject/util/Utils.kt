package com.example.creditagricolproject.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Utils {

    fun convertUnixTimestampToReadableDate(unixTimestamp: Long): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = Date(unixTimestamp * 1000L)
        return dateFormat.format(date)
    }

}