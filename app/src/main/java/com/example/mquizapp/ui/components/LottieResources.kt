package com.example.mquizapp.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.mquizapp.R
import kotlinx.coroutines.delay

@Composable
fun FireLottie(isEnabled : Boolean ,modifier: Modifier = Modifier) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.fire_lottie))

    // Animate only if enabled
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = if (isEnabled) LottieConstants.IterateForever else 1,
        isPlaying = isEnabled // control play/pause
    )

    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier.graphicsLayer {
            alpha = if (isEnabled) 1f else 0.4f // fade out if disabled
        }
    )
}

@Composable
fun CelebrationLottie(
    modifier: Modifier = Modifier,
    durationMillis: Long = 3000
) {
    var isVisible by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(durationMillis)
        isVisible = false
    }

    if (isVisible) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.celebration_lottie))
        val progress by animateLottieCompositionAsState(
            composition = composition,
            iterations = LottieConstants.IterateForever
        )

        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = modifier
        )
    }
}