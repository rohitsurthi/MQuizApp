package com.example.mquizapp.data.repository

import android.util.Log
import com.example.mquizapp.data.local.QuestionDao
import com.example.mquizapp.data.local.QuestionEntity
import com.example.mquizapp.data.remote.QuestionApiService
import com.example.mquizapp.util.NetworkResult
import kotlinx.coroutines.flow.Flow

class QuizRepositoryImpl(
    private val dao : QuestionDao,
    private val quizApi : QuestionApiService
) : QuizRepository {
    override fun getQuestions(): Flow<List<QuestionEntity>> {
        return dao.getAllQuestions()
    }

    override suspend fun refreshQuestions(): NetworkResult<Unit> {
        return try {
            Log.d("check_network", "api hit")
            val response = quizApi.getQuestions()
            val entities = response.map {
                it.toEntity()
            }
            Log.d("check_network", "success : $entities")
            dao.insertAll(entities)
            NetworkResult.Success(Unit)
        } catch (e : Exception) {
            Log.d("check_network", "error : ${e.message}")
            NetworkResult.Error(e.message ?: "Unknown Error", e)
        }
    }

}