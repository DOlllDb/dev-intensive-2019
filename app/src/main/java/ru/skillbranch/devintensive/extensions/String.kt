package ru.skillbranch.devintensive.extensions

fun String.truncate(maxLenght: Int = 16): String {
    return if (this.trim().length > maxLenght)
        "${this.substring(0, minOf(maxLenght, this.length)+1).trim()}..."
    else
        this.trim()
}

fun String.stripHtml() : String {
    return this.replace(Regex("<[^<]*?>|&(#\\d+|amp|lt|gt|quot);"), "").replace(Regex(" +"), " ")
}