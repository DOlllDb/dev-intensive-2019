package ru.skillbrabch.devintensive.models

import ru.skillbrabch.devintensive.extentions.humanizeDiff
import java.util.*

class ImageMessage(
    id: String,
    from: User?,
    chat: Chat,
    isIncomint: Boolean = false,
    date: Date = Date(),
    var image: String?
) : BaseMessage(id, from, chat, isIncomint, date) {
    override fun formatMessage(): String =
        "${from?.firstName} ${if (isIncoming) "получил" else "отправил"} изображение \"$image\" ${date.humanizeDiff()}"
}