package ru.skillbrabch.devintensive.utils

import ru.skillbrabch.devintensive.extentions.TimeUnits
import java.util.*
import java.util.function.Function

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val parts: List<String>? = fullName?.split(" ")?.filter { s: String -> !s.isBlank() }
        val firstName = parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)
        return firstName to lastName
    }

    fun transliteration(payload: String, divider: String = " "): String {
        return payload.toLowerCase().split(" ").joinToString(divider) { part ->
            part.split("").joinToString("") { character -> transliterateCharacter(character) }
                .capitalize()
        }
    }

    private fun transliterateCharacter(character: String): String = when (character) {
        "а" -> "a"
        "б" -> "b"
        "в" -> "v"
        "г" -> "g"
        "д" -> "d"
        "е" -> "e"
        "ё" -> "e"
        "ж" -> "zh"
        "з" -> "z"
        "и" -> "i"
        "й" -> "i"
        "к" -> "k"
        "л" -> "l"
        "м" -> "m"
        "н" -> "n"
        "о" -> "o"
        "п" -> "p"
        "р" -> "r"
        "с" -> "s"
        "т" -> "t"
        "у" -> "u"
        "ф" -> "f"
        "х" -> "h"
        "ц" -> "c"
        "ч" -> "ch"
        "ш" -> "sh"
        "щ" -> "sh'"
        "ъ" -> ""
        "ы" -> "i"
        "ь" -> ""
        "э" -> "e"
        "ю" -> "yu"
        "я" -> "ya"
        else -> character
    }

    fun toInitials(firstName: String?, lastName: String?): String {
        if (firstName == null && lastName == null) {
            return "null"
        }
        val initials =
            "${firstName?.trim()?.toUpperCase()?.getOrNull(0)}${lastName?.trim()?.toUpperCase()?.getOrNull(0)}"
        return when (initials.replace("null", "")) {
            "" -> "null null"
            else -> initials.replace("null", "")
        }
    }
}