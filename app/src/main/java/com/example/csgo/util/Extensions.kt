package com.example.csgo.util

import android.annotation.SuppressLint
import android.content.Context
import android.text.format.DateUtils
import com.example.csgo.R
import com.example.csgo.domain.model.Match
import com.example.csgo.presentation.matchDetails.PlayerToShow
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun Date?.formatToPattern(context: Context): String {
    val date: String = if (DateUtils.isToday(this?.time ?: Date().time)) {
        context.getString(R.string.today)
    } else {
        val sDefSystemLanguage = Locale.getDefault()
        if (isFromThisWeek(this ?: Date())) SimpleDateFormat("EEE", sDefSystemLanguage).format(
            this ?: Date()
        ).replace(".", "") else SimpleDateFormat("dd.MM").format(this ?: Date())
    }

    val hour = SimpleDateFormat("HH:mm").format(this ?: Date())
    return "$date, $hour"
}


fun List<Match.Player>.mapToShow(player: List<Match.Player>?): List<PlayerToShow> {
    val playerToShow: MutableList<PlayerToShow> = mutableListOf()
    for ((i, v) in this.withIndex()) {
        playerToShow.add(
            PlayerToShow(
                v.name,
                v.nickname,
                v.image_url,
                player?.get(i)?.name,
                player?.get(i)?.nickname,
                player?.get(i)?.image_url
            )
        )
    }
    return playerToShow
}

fun isFromThisWeek(date: Date): Boolean {
    val c: Calendar = Calendar.getInstance()
    c.firstDayOfWeek = Calendar.MONDAY

    c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
    c.set(Calendar.HOUR_OF_DAY, 0)
    c.set(Calendar.MINUTE, 0)
    c.set(Calendar.SECOND, 0)
    c.set(Calendar.MILLISECOND, 0)

    val monday: Date = c.time

    val nextMonday = Date(monday.time + 7 * 24 * 60 * 60 * 1000)

    return date.after(monday) && date.before(nextMonday)


}
