package ru.skillbranch.devintensive.models

import android.util.Log
import ru.skillbranch.devintensive.utils.Utils

data class Profile(
    val firstName: String,
    val lastName: String,
    val about: String,
    val repository: String,
    val rating: Int = 0,
    val respect: Int = 0
) {
    val nickName: String = Utils.transliteration("$firstName $lastName".trim())
    val rank: String = "Junior Android Developer"

    fun toMap() : Map<String, Any> {
        Log.d("M_Profile", nickName)
        return mapOf(
            "nickName" to nickName,
            "rank" to rank,
            "firstName" to firstName,
            "lastName" to lastName,
            "about" to about,
            "repository" to repository,
            "rating" to rating,
            "respect" to respect
        )
    }
}