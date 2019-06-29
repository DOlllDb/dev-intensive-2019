package ru.skillbrabch.devintensive.extentions

import ru.skillbrabch.devintensive.models.User
import ru.skillbrabch.devintensive.models.UserView
import ru.skillbrabch.devintensive.utils.Utils

fun User.toUserView(): UserView {
    val nickname = Utils.transliteration("$firstName $lastName")
    val initials = Utils.toInitials(firstName, lastName)
    val status =
        "${if (lastVisit == null) "Еще ни разу не был" else if (isOnline) "online" else "Последний раз был ${lastVisit.humanizeDiff()}"}"

    return UserView(
        id,
        fullName = "$firstName $lastName",
        nickName = nickname,
        avatar = avatar,
        status = status,
        initials = initials
    )
}