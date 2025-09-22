package com.example.mquizapp.ui.screens

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mquizapp.ui.components.OptionColors
import com.example.mquizapp.ui.components.StreakFlow
import com.example.mquizapp.viewModel.QuizViewModel
import kotlin.math.min


@Composable
fun QuizScreen(
    viewModel: QuizViewModel,
    onQuizEnd: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val q = viewModel.currentQuestion

    // Navigate to result once finished
    LaunchedEffect(state.isFinished) {
        if (state.isFinished) onQuizEnd()
    }

    // Defensive fallbacks
    if (q == null && !state.isFinished) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }
    if (state.errorMessage != null && state.questions.isEmpty()) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Error: ${state.errorMessage}")
        }
        return
    }

    val threshold = 100f
    var swipeAmound by remember { mutableStateOf(0f) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragEnd = {
                        if (swipeAmound > threshold) {
                            viewModel.skipQuestion()
                        }
                        swipeAmound = 0f
                    }
                ) { _, dragAmount ->
                    swipeAmound += dragAmount
                }
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Text(
                "MQuiz",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(12.dp))

            // Streak UI
            StreakFlow(streak = state.streak, state.questions.size)

            Spacer(Modifier.height(16.dp))

            // Progress
            Text(
                "Question ${
                    min(
                        state.currentIndex + 1,
                        state.questions.size
                    )
                } of ${state.questions.size}",
                style = MaterialTheme.typography.titleMedium,
                fontSize = 14.sp
            )
            Spacer(Modifier.height(6.dp))
            val progress = if (state.questions.isEmpty()) 0f
            else (min(
                state.currentIndex + 1,
                state.questions.size
            )) / state.questions.size.toFloat()
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxWidth(),
                color = ProgressIndicatorDefaults.linearColor,
                trackColor = ProgressIndicatorDefaults.linearTrackColor,
                strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
            )

            Spacer(Modifier.height(20.dp))

            // Question
            Text(
                q?.question.orEmpty(),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Normal
            )
            Spacer(Modifier.height(15.dp))


            q?.options?.forEachIndexed { index, option ->
                val (bg, fg) = OptionColors(
                    revealed = state.isAnswerRevealed,
                    isSelected = index == state.selectedIndex,
                    isCorrect = index == q.correctOptionIndex
                )
                Button(
                    onClick = { viewModel.onOptionSelected(index) },
                    enabled = !state.isLocked && !state.isAnswerRevealed,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = bg,
                        contentColor = fg,
                        disabledContainerColor = bg,
                        disabledContentColor = fg
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    shape = MaterialTheme.shapes.large
                ) {
                    Text(option, modifier = Modifier.padding(vertical = 6.dp))
                }
            }

            Spacer(Modifier.height(24.dp))

        }


        Column(
            modifier = Modifier
                .align(alignment = Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            verticalArrangement = Arrangement.Center
        ) {
            // chip
            if (state.isAnswerRevealed) {
                Spacer(Modifier.height(8.dp))
                AssistChip(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(bottom = 10.dp, end = 10.dp),
                    onClick = {},
                    enabled = true,
                    label = {
                        Text("Next Question in ${state.revealSecondsLeft ?: 2}s")
                    }
                )
            }

            OutlinedButton(
                onClick = { viewModel.skipQuestion() },
                enabled = !state.isAnswerRevealed && !state.isLocked,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp),
                shape = MaterialTheme.shapes.large
            ) { Text("Skip") }
        }
    }
}













