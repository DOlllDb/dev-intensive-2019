package ru.skillbrabch.devintensive.models

import java.util.*

abstract class BaseMessage(
    val id: String,
    val from: User?,
    val chat: Chat,
    val isIncoming: Boolean = false,
    val date: Date = Date()
) {
    abstract fun formatMessage(): String

    companion object AbstractFactory {
        var lastId = -1;
        fun makeMessage(
            from: User?,
            chat: Chat,
            date: Date = Date(),
            type: String = "text",
            payload: Any?,
            isIncoming: Boolean = false
        ): BaseMessage {
            lastId++
            val message = when (type) {
                "image" -> ImageMessage("$lastId", from, chat, date = date, image = payload as String, isIncomint = isIncoming)
                else -> TextMessage("$lastId", from, chat, date = date, text = payload as String, isIncoming = isIncoming)
            }
            println(message.formatMessage())
            return message
        }
    }
}