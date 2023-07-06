package com.sanryoo.whether.feature.util

import java.util.Calendar
import java.util.Date

fun Date.getDateCalendar() :Int {
    val calendar = Calendar.getInstance().apply {
        time = this@getDateCalendar
    }
    return calendar.get(Calendar.DATE)
}