package com.example.mquizapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "question_table")
data class QuestionEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val question : String,
    val options : List<String>,
    val correctOptionIndex : Int
)