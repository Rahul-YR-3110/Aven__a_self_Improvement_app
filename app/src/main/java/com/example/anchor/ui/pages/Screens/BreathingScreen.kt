package com.example.anchor.ui.pages.Screens

import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anchor.ui.theme.AnchorTheme
import kotlinx.coroutines.delay

enum class BreathingPhase {
    INHALE, EXHALE,FINISH
}

@Composable
fun BreathingScreen() {
    var phase by remember { mutableStateOf(BreathingPhase.EXHALE) }

    LaunchedEffect(Unit) {
            phase = BreathingPhase.INHALE
            delay(5000)
            phase = BreathingPhase.EXHALE
            delay(5000)
            phase=BreathingPhase.FINISH
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.BottomCenter
        ) {
            val fullScreenHeight = maxHeight
            val fullScreenwidith=maxWidth
            val minHeight = 70.dp

            val animatedCornerRadius by animateDpAsState(
                if (phase==BreathingPhase.INHALE) 0.dp else fullScreenwidith/3.5f,
                animationSpec = tween( durationMillis = 5000, easing= EaseIn),
                label="BreathingBoxCornerRadius"
            )
            val animatedHeight by animateDpAsState(
                targetValue = if (phase == BreathingPhase.INHALE) fullScreenHeight else minHeight,
                animationSpec = tween(
                    durationMillis = 5000,
                    easing = FastOutSlowInEasing
                ),
                label = "BreathingBoxHeight"
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeight(animatedHeight)
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(topStart = animatedCornerRadius, topEnd =animatedCornerRadius)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (phase == BreathingPhase.INHALE) "Inhale..."
                        else if (phase == BreathingPhase.EXHALE) "Exhale..."
                        else "Finish",
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview
@Composable
fun BreathingScreenPreview() {
    AnchorTheme(darkTheme = true) {
        BreathingScreen()
    }
}