package com.example.dp.core.utils

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.Locale

const val dayInMilliseconds = 86400000

fun createListOfDates(
    startDate: LocalDate,
    endDate: LocalDate,
    dayOfWeek: DayOfWeek,
    isUpperWeek: Boolean,
): List<LocalDate> {
    val resultDates = mutableListOf<LocalDate>()
    var nextDate = startDate
    var daysToAdvance = 1
    while (!nextDate.isAfter(endDate)) {
        if (nextDate.dayOfWeek == dayOfWeek) {
            daysToAdvance = 7
            resultDates.add(nextDate)
        }
        nextDate = nextDate.plusDays(daysToAdvance.toLong())
    }
    return resultDates.filter {
        if (isUpperWeek) {
            it.get(WeekFields.of(Locale.getDefault()).weekOfYear()) % 2 == 0
        } else {
            it.get(WeekFields.of(Locale.getDefault()).weekOfYear()) % 2 != 0
        }
    }
}