package com.example.mquizapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mquizapp.ui.screens.QuizScreen
import com.example.mquizapp.ui.screens.ResultScreen
import com.example.mquizapp.ui.screens.SplashScreen
import com.example.mquizapp.ui.theme.MQuizAppTheme
import com.example.mquizapp.ui.theme.QuizAppTheme
import com.example.mquizapp.viewModel.QuizViewModel
import com.example.mquizapp.viewModel.QuizViewModelFactory

class MainActivity : ComponentActivity() {

    private val viewModel: QuizViewModel by viewModels {
        val appContainer = (application as MQuizApplication).container
        QuizViewModelFactory(appContainer.repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuizAppTheme {
                Scaffold { pad ->
                    MQuizApp(viewModel, modifier = Modifier.padding(pad).fillMaxSize())
                }
            }
        }
    }

    @Composable
    fun MQuizApp(
        viewModel: QuizViewModel,
        modifier: Modifier
    ) {

        val navController = rememberNavController()

        Box(modifier = modifier) {
            NavHost(
                navController,
                startDestination = "splash"
            ) {
                composable("splash") {
                    SplashScreen(viewModel) {
                        navController.navigate("questions") {
                            popUpTo("splash") { inclusive = true }
                        }
                    }
                }
                composable("questions") {
                    QuizScreen(viewModel) {
                        navController.navigate("results") {
                            popUpTo("questions") { inclusive = true }
                        }
                    }
                }
                composable("results") {
                    ResultScreen(viewModel) {
                        navController.navigate("questions") {
                            popUpTo("results") { inclusive = true }
                        }
                    }
                }
            }
        }

    }
}
