package com.example.csgo.util

import android.annotation.SuppressLint
import android.content.Context
import android.text.format.DateUtils
import com.example.csgo.R
import java.text.SimpleDateFormat
import java.util.*

private const val defaultValue = 0
private const val defaultValueSeven = 7
private const val defaultValueTwentyFour = 24
private const val defaultValueSixty = 60
private const val defaultValueThousand = 1000
private const val dot = "."
private const val emptyString = "."
private const val datePattern = "EEE"
private const val dateInNumberPattern = "dd.MM"
private const val hourPattern = "HHmm"

@SuppressLint("SimpleDateFormat")
fun Date?.formatToPattern(context: Context): String {
    val date: String = if (DateUtils.isToday(this?.time ?: Date().time)) {
        context.getString(R.string.today)
    } else {
        val sDefSystemLanguage = Locale.getDefault()
        if (this?.isFromThisWeek() == true) SimpleDateFormat(
            datePattern,
            sDefSystemLanguage
        ).format(
            this
        ).replace(dot, emptyString) else SimpleDateFormat(dateInNumberPattern).format(
            this ?: Date()
        )
    }

    val hour = SimpleDateFormat(hourPattern).format(this ?: Date())
    return "$date, $hour"
}


fun Date.isFromThisWeek(): Boolean {
    val monday: Date

    with(Calendar.getInstance()) {
        firstDayOfWeek = Calendar.MONDAY

        set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        set(Calendar.HOUR_OF_DAY, defaultValue)
        set(Calendar.MINUTE, defaultValue)
        set(Calendar.SECOND, defaultValue)
        set(Calendar.MILLISECOND, defaultValue)


        monday = this.time

    }

    val nextMonday =
        Date(monday.time + defaultValueSeven * defaultValueTwentyFour * defaultValueSixty * defaultValueSixty * defaultValueThousand)

    return this.after(monday) && this.before(nextMonday)


}
