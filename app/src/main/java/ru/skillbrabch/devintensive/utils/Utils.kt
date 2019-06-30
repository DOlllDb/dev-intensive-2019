package ru.skillbrabch.devintensive.utils

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val parts: List<String>? = fullName?.split(" ")?.filter { s: String -> !s.isBlank() }
        val firstName = parts?.getOrNull(0)
        val lastName = parts?.getOrNull(1)
        println("$firstName $lastName")
        return firstName to lastName
    }

    fun transliteration(payload: String, divider: String = " "): String {
        return payload.toCharArray().map{ character -> if (character.isUpperCase()) transliterateCharacter(character.toLowerCase()).capitalize() else transliterateCharacter(character) }.joinToString("").replace(" ", divider)
    }

    private fun transliterateCharacter(character: Char): String = when (character) {
        'а' -> "a"
        'б' -> "b"
        'в' -> "v"
        'г' -> "g"
        'д' -> "d"
        'е' -> "e"
        'ё' -> "e"
        'ж' -> "zh"
        'з' -> "z"
        'и' -> "i"
        'й' -> "i"
        'к' -> "k"
        'л' -> "l"
        'м' -> "m"
        'н' -> "n"
        'о' -> "o"
        'п' -> "p"
        'р' -> "r"
        'с' -> "s"
        'т' -> "t"
        'у' -> "u"
        'ф' -> "f"
        'х' -> "h"
        'ц' -> "c"
        'ч' -> "ch"
        'ш' -> "sh"
        'щ' -> "sh'"
        'ъ' -> ""
        'ы' -> "i"
        'ь' -> ""
        'э' -> "e"
        'ю' -> "yu"
        'я' -> "ya"
        else -> character.toString()
    }

    fun toInitials(firstName: String?, lastName: String?): String {
        val initials =
            "${firstName?.trim()?.toUpperCase()?.getOrNull(0)}${lastName?.trim()?.toUpperCase()?.getOrNull(0)}"
        val result =  when (initials.replace("null", "")) {
            "" -> "null"
            else -> initials.replace("null", "")
        }
        println(result)
        return result
    }
}