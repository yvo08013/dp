package com.example.dp.core.utils

import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
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

fun LocalDate.toLong() = this.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

fun Long.toLocalDate() = Instant.ofEpochMilli(this).atZone(ZoneId.systemDefault()).toLocalDate()

val locale = Locale("ru")

val absenceGroupDateFormat = SimpleDateFormat("yyyy", locale)

val absenceItemDateFormat = SimpleDateFormat("dd MMMM, hh:ss", locale)

var monthNames = arrayOf(
    "Январь",
    "Февраль",
    "Март",
    "Апрель",
    "Май",
    "Июнь",
    "Июль",
    "Август",
    "Сентябрь",
    "Октябрь",
    "Ноябрь",
    "Декабрь"
)