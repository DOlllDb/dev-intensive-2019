package ru.skillbranch.devintensive.models

class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME, var failsCount: Int = 0) {

    fun askQuestion(): String = when (question) {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }

    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> {
        val validation = question.validateAnswer(answer)
        var resultText : String
        if (validation !== null) {
            resultText = validation
        } else {
            if (question.answers.contains(answer.toLowerCase())) {
                question = question.nextQuestion()
                resultText = "Отлично - ты справился\n"
            } else {
                failsCount++
                resultText = "Это неправильный ответ"
                if (failsCount == 4) {
                    question = Question.NAME
                    status = Status.NORMAL
                    resultText += ". Давай все по новой"
                } else {
                    status = status.nextStatus()
                }
                resultText +="\n"
            }
        }
        return "$resultText${question.question}" to status.color
    }

    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0));

        fun nextStatus(): Status {
            return if (this.ordinal < values().lastIndex) {
                values()[this.ordinal + 1]
            } else {
                values()[0]
            }
        }
    }

    enum class Question(val question: String, val answers: List<String>) {
        NAME("Как меня зовут?", listOf("бендер", "bender")) {
            override fun nextQuestion(): Question = PROFESSION
            override fun validateAnswer(answer: String): String? {
                return if (answer.isEmpty() || (answer[0].isLetter() && answer[0].isUpperCase())) {
                    null
                } else {
                    "Имя должно начинаться с заглавной буквы\n"
                }
            }
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {
            override fun nextQuestion(): Question = MATERIAL
            override fun validateAnswer(answer: String): String? {
                return if (answer.isEmpty() || (answer[0].isLetter() && answer[0].isLowerCase())) {
                    null
                } else {
                    "Профессия должна начинаться со строчной буквы\n"
                }
            }
        },
        MATERIAL("Из чего я сделан?", listOf("метадд", "дерево", "metal", "iron", "wood")) {
            override fun nextQuestion(): Question = BDAY
            override fun validateAnswer(answer: String): String? {
                return if (answer.isEmpty() || answer.none { it.isDigit() }) {
                    null
                } else {
                    "Материал не должен содержать цифр\n"
                }
            }
        },
        BDAY("Когда меня создали?", listOf("2993")) {
            override fun nextQuestion(): Question = SERIAL
            override fun validateAnswer(answer: String): String? {
                return if (answer.isEmpty() || answer.all { it.isDigit() }) {
                    null
                } else {
                    "Год моего рождения должен содержать только цифры\n"
                }
            }
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {
            override fun nextQuestion(): Question = IDLE
            override fun validateAnswer(answer: String): String? {
                return if (answer.isEmpty() || (answer.length == 7 && answer.all { it.isDigit() })) {
                    null
                } else {
                    "Серийный номер содержит только цифры, и их 7\n"
                }
            }
        },
        IDLE("На этом все, вопросов больше нет", listOf()) {
            override fun nextQuestion(): Question = IDLE
            override fun validateAnswer(answer: String): String? {
                return ""
            }
        };

        abstract fun nextQuestion(): Question
        abstract fun validateAnswer(answer: String): String?
    }
}