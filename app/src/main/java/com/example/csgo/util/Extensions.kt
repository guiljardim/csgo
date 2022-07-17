package com.example.csgo.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun Date?.formatToPattern(): String {
    val date = SimpleDateFormat("dd.MM").format(this ?: Date())
    val hour = SimpleDateFormat("HH:mm").format(this ?: Date())
    return "$date, $hour"
}