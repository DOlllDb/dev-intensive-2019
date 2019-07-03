package ru.skillbranch.devintensive.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    this.time += units.toMillis(value)
    return this
}

fun Date.humanizeDiff(date: Date = Date()): String {
    val isInPast = date.time >= this.time
    return when (val diff = (Math.abs(date.time - this.time))) {
        in TimeUnits.SECOND.toMillis(0)..TimeUnits.SECOND.toMillis(1) -> "только что"
        in TimeUnits.SECOND.toMillis(0)..TimeUnits.SECOND.toMillis(45) -> if (isInPast) "несколько секунд назад"  else "через несколько секунд"
        in TimeUnits.SECOND.toMillis(45)..TimeUnits.SECOND.toMillis(75) -> if (isInPast) "минуту назад"  else "через минуту"
        in TimeUnits.SECOND.toMillis(75)..TimeUnits.MINUTE.toMillis(45) -> TimeUnits.MINUTE.getDeclentedRepresentation(diff, isInPast)
        in TimeUnits.MINUTE.toMillis(45)..TimeUnits.MINUTE.toMillis(75) -> if (isInPast) "час назад" else "через час"
        in TimeUnits.MINUTE.toMillis(75)..TimeUnits.HOUR.toMillis(22) -> TimeUnits.HOUR.getDeclentedRepresentation(diff, isInPast)
        in TimeUnits.HOUR.toMillis(22)..TimeUnits.HOUR.toMillis(26) -> if (isInPast) "день назад" else "через день"
        in TimeUnits.HOUR.toMillis(26)..TimeUnits.DAY.toMillis(360) -> TimeUnits.DAY.getDeclentedRepresentation(diff, isInPast)
        else -> if (isInPast) "более года назад" else "более чем через год"
    }
}

enum class TimeUnits(
    private val convertValue: Long,
    private val declentionValues: List<String>
) {
    SECOND(1000, listOf("секунду", "секунды", "секунд")),
    MINUTE(1000 * 60, listOf("минуту", "минуты", "минут")),
    HOUR(1000 * 60 * 60, listOf("час", "часа", "часов")),
    DAY(1000 * 60 * 60 * 24, listOf("день", "дня", "дней"));

    fun toMillis(value: Int) = convertValue * value
    fun getValueFromMillis(value: Long) = value / convertValue
    fun getDeclentedRepresentation(value: Long, isInPast: Boolean) =
        "${if (isInPast) "" else "через " }${plural(getValueFromMillis(value).toInt())}${if (isInPast) " назад" else ""}"

    fun plural(value: Int): String {
        return "$value " + when {
            Math.abs(value) % 100 in 5..20 -> declentionValues[2]
            Math.abs(value) % 10 == 1 -> declentionValues[0]
            Math.abs(value) % 10 in 2..4 -> declentionValues[1]
            else -> declentionValues[2]
        }
    }
}