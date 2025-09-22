package com.example.mquizapp.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun StreakFlow(streak: Int, totalQuestion : Int) {

    Row(verticalAlignment = Alignment.CenterVertically) {
        var message = ""
        when (streak) {
            1 -> {
                message = "Streak Started. Let's go!"
            }
            2 -> {
                message = "Nice Flow."
            }
            5 -> {
                message = "You are on Fire."
            }
            else -> {
                if(streak > 1) message = "achieved!"
            }
        }
        FireLottie(
            streak >= 1,
            modifier = Modifier
                .width(35.dp)
                .height(35.dp)
        )
        Text(
            text = "Streak $streak! $message",
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 5.dp)
        )

        Spacer(Modifier.width(5.dp))
    }
}
