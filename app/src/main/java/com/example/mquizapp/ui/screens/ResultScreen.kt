package com.example.mquizapp.ui.screens

import android.text.Layout
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mquizapp.ui.components.CelebrationLottie
import com.example.mquizapp.ui.components.StatCard
import com.example.mquizapp.viewModel.QuizViewModel

@Composable
fun ResultScreen(
    viewModel: QuizViewModel,
    onRestart: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val total = state.questions.size

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Quiz Results", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(24.dp))

        Text("Congratulations!", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text(
            "You've completed the quiz. Here's your performance summary:",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            StatCard(title = "Correct\nAnswers", value = "${state.correctAnswers}/$total", modifier = Modifier.weight(1f))
            StatCard(title = "Highest\nStreak", value = "${state.longestStreak}", modifier = Modifier.weight(1f))
        }

        Spacer(Modifier.height(32.dp))

        OutlinedCard(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Skipped", style = MaterialTheme.typography.bodyLarge)
                Text("${state.skipped}", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(Modifier.height(32.dp))

        Button(
            onClick = {
                viewModel.resetQuiz()
                onRestart()
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Restart Quiz")
        }

        CelebrationLottie(
            modifier = Modifier.fillMaxWidth(),
            4000
        )
    }
}
