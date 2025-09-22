package com.example.mquizapp.data.remote

import com.example.mquizapp.model.Question
import retrofit2.http.GET

interface QuestionApiService {

    @GET("53846277a8fcb034e482906ccc0d12b2/raw")
    suspend fun getQuestions() : List<Question>
}