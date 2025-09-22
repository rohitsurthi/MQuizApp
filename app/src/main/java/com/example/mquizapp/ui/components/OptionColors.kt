package com.example.mquizapp.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.mquizapp.ui.theme.correct_answer_color
import com.example.mquizapp.ui.theme.wrong_answer_color

@Composable
fun OptionColors(
    revealed: Boolean,
    isSelected: Boolean,
    isCorrect: Boolean
): Pair<Color, Color> {
    val cs = MaterialTheme.colorScheme
    return when {
        revealed && isCorrect -> correct_answer_color to Color.White
        revealed && isSelected && !isCorrect -> wrong_answer_color to Color.White
        else -> cs.surfaceVariant to cs.onSurfaceVariant
    }
}
