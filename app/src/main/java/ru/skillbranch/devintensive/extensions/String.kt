package ru.skillbranch.devintensive.extensions

fun String.truncate(maxLenght: Int = 16): String {
    return if (this.trim().length > maxLenght)
        "${this.substring(0, minOf(maxLenght, this.length)).trim()}..."
    else
        this.trim()
}

fun String.stripHtml() = this.replace(Regex("<[^<]*?>|&\\d+;"), "").replace(Regex("\\s+"), " ")