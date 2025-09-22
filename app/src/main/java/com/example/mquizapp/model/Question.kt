package com.example.mquizapp.model

import com.google.gson.annotations.SerializedName

data class Question(
    @SerializedName("id")
    val id : Int,
    @SerializedName("question")
    val question : String,
    @SerializedName("options")
    val options : List<String>,
    @SerializedName("correctIndex")
    val correctOptionIndex : Int
)