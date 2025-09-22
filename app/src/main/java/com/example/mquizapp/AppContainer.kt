package com.example.mquizapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mquizapp.data.local.AppDatabase
import com.example.mquizapp.data.local.QuestionDao
import com.example.mquizapp.data.remote.QuestionApiService
import com.example.mquizapp.data.remote.RetrofitProvider
import com.example.mquizapp.data.repository.QuizRepository
import com.example.mquizapp.data.repository.QuizRepositoryImpl
import com.example.mquizapp.ui.screens.ResultScreen
import com.example.mquizapp.viewModel.QuizViewModel

class AppContainer(
    context: Context
) {

    // database
    private val database : AppDatabase by lazy {
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "quiz_db"
        ).build()
    }

    private val dao : QuestionDao by lazy {
        database.questionDao()
    }

    // network
    private val questionAPI : QuestionApiService by lazy {
        RetrofitProvider.questionApi
    }

    // repository
    val repository : QuizRepository by lazy {
        QuizRepositoryImpl(dao, questionAPI)
    }

}