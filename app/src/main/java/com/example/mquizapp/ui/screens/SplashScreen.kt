package com.example.mquizapp.ui.screens

import android.R
import android.util.Size
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.navArgument
import com.example.mquizapp.MainActivity
import com.example.mquizapp.ui.theme.theme_color
import com.example.mquizapp.viewModel.QuizViewModel
import kotlinx.coroutines.delay
import kotlin.coroutines.coroutineContext

@Composable
fun SplashScreen(
    viewModel: QuizViewModel,
    onNavigateToQuestion: () -> Unit
) {

    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(state.questions.size, state.isLoading) {

        if (!state.isLoading && state.questions.isNotEmpty()) {
            onNavigateToQuestion()
        }
    }

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "MQuiz",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            )
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(top = 20.dp))
            } else if (state.errorMessage != null && state.questions.isEmpty()) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Failed to load questions.")
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = { viewModel.refreshQuestions() }) { Text("Retry") }
                }
            }
        }
    }
}