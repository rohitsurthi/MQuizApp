package com.example.mquizapp.viewModel

import androidx.compose.ui.unit.max
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mquizapp.data.local.QuestionEntity
import com.example.mquizapp.data.repository.QuizRepository
import com.example.mquizapp.util.NetworkResult
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class QuizViewModel(
    private val repository: QuizRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    val currentQuestion: QuestionEntity?
        get() = _uiState.value.questions.getOrNull(_uiState.value.currentIndex)

    private var autoAdvanceJob: Job? = null

    init {
        observeQuestions()
        refreshQuestions() // fetch on startup; DB remains the source of truth
    }

    private fun observeQuestions() {
        viewModelScope.launch {
            repository.getQuestions().collect { list ->
                _uiState.update { it.copy(questions = list) }
            }
        }
    }

    fun refreshQuestions() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val result = repository.refreshQuestions()
            delay(2000) // adding a fake delay before updating the state to show a bit of progress bar in splash
            when (result) {
                is NetworkResult.Success -> _uiState.update { it.copy(isLoading = false) }
                is NetworkResult.Error   -> _uiState.update { it.copy(isLoading = false, errorMessage = result.message) }
                is NetworkResult.Loading -> _uiState.update { it.copy(isLoading = true) }
            }
        }
    }

    fun onOptionSelected(index: Int) {
        val q = currentQuestion ?: return
        val state = _uiState.value
        if (state.isLocked || state.isAnswerRevealed) return // ignore extra taps

        val isCorrect = index == q.correctOptionIndex
        val newStreak = if (isCorrect) state.streak + 1 else 0
        val newLongest = maxOf(state.longestStreak, newStreak)

        _uiState.update {
            it.copy(
                selectedIndex = index,
                isAnswerRevealed = true,
                isLocked = true,
                correctAnswers = if (isCorrect) it.correctAnswers + 1 else it.correctAnswers,
                streak = newStreak,
                longestStreak = newLongest
            )
        }

        scheduleAutoAdvance()
    }

    fun skipQuestion() {
        cancelAutoAdvance()
        _uiState.update {
            it.copy(
                skipped = it.skipped + 1,
                streak = 0 // wrong/skip resets streak
            )
        }
        goToNextQuestion(resetReveal = true)
    }

    fun resetQuiz() {
        cancelAutoAdvance()
        _uiState.update {
            it.copy(
                isLoading = false,
                errorMessage = null,
                currentIndex = 0,
                selectedIndex = -1,
                isAnswerRevealed = false,
                isLocked = false,
                correctAnswers = 0,
                skipped = 0,
                streak = 0,
                longestStreak = 0,
                isFinished = false,
                revealSecondsLeft = null
            )
        }
    }

    private fun scheduleAutoAdvance() {
        cancelAutoAdvance()
        autoAdvanceJob = viewModelScope.launch {
            var secs = 2
            while (secs >= 0) {
                _uiState.update { it.copy(revealSecondsLeft = secs) }
                delay(1000)
                secs--
            }
            goToNextQuestion(resetReveal = true)
        }
    }

    private fun cancelAutoAdvance() {
        autoAdvanceJob?.cancel()
        autoAdvanceJob = null
    }

    private fun goToNextQuestion(resetReveal: Boolean) {
        val state = _uiState.value
        val lastIndex = state.questions.lastIndex
        if (state.currentIndex < lastIndex) {
            _uiState.update {
                it.copy(
                    currentIndex = it.currentIndex + 1,
                    selectedIndex = if (resetReveal) -1 else it.selectedIndex,
                    isAnswerRevealed = if (resetReveal) false else it.isAnswerRevealed,
                    isLocked = false,
                    revealSecondsLeft = null
                )
            }
        } else {
            // finish the quiz
            _uiState.update {
                it.copy(
                    currentIndex = state.questions.size,
                    selectedIndex = -1,
                    isAnswerRevealed = false,
                    isLocked = false,
                    isFinished = true
                )
            }
        }
    }
}