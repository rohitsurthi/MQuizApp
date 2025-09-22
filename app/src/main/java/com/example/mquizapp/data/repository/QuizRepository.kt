package com.example.mquizapp.data.repository

import com.example.mquizapp.data.local.QuestionEntity
import com.example.mquizapp.model.Question
import com.example.mquizapp.util.NetworkResult
import kotlinx.coroutines.flow.Flow

interface QuizRepository {
    fun getQuestions() : Flow<List<QuestionEntity>>

    suspend fun refreshQuestions() : NetworkResult<Unit>
}