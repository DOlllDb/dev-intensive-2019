package ru.skillbrabch.devintensive.extentions

import java.text.SimpleDateFormat
import java.util.*

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time

    time += units.toMillis(value)
    this.time = time
    return this
}

fun Date.humanizeDiff(date: Date = Date()) : String {
    val diff = date.time - this.time;
    return when (diff) {
        in TimeUnits.SECOND.toMillis(0)..TimeUnits.SECOND.toMillis(1) -> "только что"
        in TimeUnits.SECOND.toMillis(1)..TimeUnits.SECOND.toMillis(45) -> "несколько секунд назад"
        in TimeUnits.SECOND.toMillis(45)..TimeUnits.SECOND.toMillis(75) -> "минуту назад"
        in TimeUnits.SECOND.toMillis(75)..TimeUnits.MINUTE.toMillis(45) -> TimeUnits.MINUTE.getDeclentedRepresentation(diff)
        in TimeUnits.MINUTE.toMillis(45)..TimeUnits.MINUTE.toMillis(75) -> "час назад"
        in TimeUnits.MINUTE.toMillis(75)..TimeUnits.HOUR.toMillis(22) -> TimeUnits.HOUR.getDeclentedRepresentation(diff)
        in TimeUnits.HOUR.toMillis(22)..TimeUnits.HOUR.toMillis(26) -> "день назад"
        in TimeUnits.HOUR.toMillis(26)..TimeUnits.DAY.toMillis(360) -> TimeUnits.DAY.getDeclentedRepresentation(diff)
        else -> "более года назад"
    }
}

enum class TimeUnits(
    private val convertValue : Long,
    private val declentionValues: List<String>
) {
    SECOND(1000, listOf("секунду", "секунды", "секунд")),
    MINUTE(1000 * 60, listOf("минуту", "минуты", "минут")),
    HOUR(1000 * 60 * 60, listOf("час", "часа", "часов")),
    DAY(1000 * 60 * 60 * 24, listOf("день", "дня", "дней"));

    fun toMillis(value: Int) = convertValue * value
    fun getValueFromMillis(value: Long) = value / convertValue
    fun getDeclentedRepresentation(value: Long) = "${getValueFromMillis(value)} ${getDeclentedStringValue(getValueFromMillis(value))} назад"


    private fun getDeclentedStringValue(value : Long) : String {
        var resultValue = Math.abs(value)
        while (resultValue / 100 != 0L) {
            resultValue %= 100
        }
        return when(resultValue) {
            1L -> declentionValues[0]
            in 2..4 -> declentionValues[1]
            0L, in 5..20 -> declentionValues[2]
            else -> getDeclentedStringValue(resultValue % 10)
        }
    }
}