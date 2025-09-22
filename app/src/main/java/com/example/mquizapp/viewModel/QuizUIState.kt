package com.example.mquizapp.viewModel

import com.example.mquizapp.data.local.QuestionEntity

data class QuizUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,

    val questions: List<QuestionEntity> = emptyList(),
    val currentIndex: Int = 0,

    // answer/reveal state
    val selectedIndex: Int = -1,          // user’s selection for current question
    val isAnswerRevealed: Boolean = false,
    val isLocked: Boolean = false,        // prevent taps while revealing / waiting

    // scoring
    val correctAnswers: Int = 0,
    val skipped: Int = 0,

    // streak
    val streak: Int = 0,
    val longestStreak: Int = 0,

    // flow
    val isFinished: Boolean = false,
    val revealSecondsLeft: Int? = null

)