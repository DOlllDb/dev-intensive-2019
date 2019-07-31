package ru.skillbranch.devintensive.utils

import android.content.Context

object Utils {
    private val transliterationMap: Map<Char, String> = hashMapOf(
        'а' to "a",
        'б' to "b",
        'в' to "v",
        'г' to "g",
        'д' to "d",
        'е' to "e",
        'ё' to "e",
        'ж' to "zh",
        'з' to "z",
        'и' to "i",
        'й' to "i",
        'к' to "k",
        'л' to "l",
        'м' to "m",
        'н' to "n",
        'о' to "o",
        'п' to "p",
        'р' to "r",
        'с' to "s",
        'т' to "t",
        'у' to "u",
        'ф' to "f",
        'х' to "h",
        'ц' to "c",
        'ч' to "ch",
        'ш' to "sh",
        'щ' to "sh'",
        'ъ' to "",
        'ы' to "i",
        'ь' to "",
        'э' to "e",
        'ю' to "yu",
        'я' to "ya"
    )

    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val parts: List<String>? = fullName?.split(" ")?.filter { s: String -> !s.isBlank() }
        val firstName = parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)
        return firstName to lastName
    }

    fun transliteration(payload: String, divider: String = " "): String {
        return payload.map {
            if (it.isUpperCase())
                (transliterationMap[it.toLowerCase()] ?: it.toString()).toLowerCase().capitalize()
            else
                (transliterationMap[it.toLowerCase()] ?: it.toString()).toLowerCase()
        }.joinToString("").replace(" ", divider)
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        val initials =
            "${firstName?.trim()?.toUpperCase()?.getOrNull(0)}${lastName?.trim()?.toUpperCase()?.getOrNull(0)}"
        return when (initials.replace("null", "")) {
            "" -> null
            else -> initials.replace("null", "")
        }
    }

    fun validateUrl(url: String) : Boolean {
        return url.isBlank() || url.matches(Regex("^(?:https://)?(?:www\\.)?github\\.com/(?!(enterprise|features|topics|collections|trending|events|marketplace|pricing|nonprofit|customer-stories|security|login|join))[^/]+(?:/)?\$"))
    }

    fun convertPxToDp(context: Context, pxValue: Int) = (pxValue / context.resources.displayMetrics.density + 0.5f).toInt()

    fun convertDpToPx(context: Context, dpValue: Int) = (dpValue * context.resources.displayMetrics.density + 0.5f).toInt()

    fun convertSpToPx(context: Context, spValue: Int) = spValue * context.resources.displayMetrics.scaledDensity.toInt()
}
