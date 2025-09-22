package com.example.mquizapp.data.repository

import com.example.mquizapp.data.local.QuestionEntity
import com.example.mquizapp.model.Question

fun Question.toEntity() : QuestionEntity {
    return QuestionEntity(
        id = id,
        question = question,
        options = options,
        correctOptionIndex = correctOptionIndex
    )
}