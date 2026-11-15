package com.example.geoblur

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CustomProgressBarView(
    progress: Float,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.LightGray,
    foregroundColor: Color = Color.Blue
    ) {
    val animatedProgress by animateFloatAsState(
        targetValue = progress
            .coerceIn(0f, 1f),
        animationSpec = tween(
            durationMillis = 800,
            easing = LinearOutSlowInEasing
        ),
        label = "ProgressAnimation"
    )
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(20.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(backgroundColor)
    ) {
        Box(
            modifier = modifier
                .fillMaxHeight()
                .fillMaxWidth(animatedProgress)
                .height(20.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(foregroundColor)
        ) { }
    }
}

@Preview(showBackground = true)
@Composable
private fun CustomProgressBarPreview() {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        (0..10).forEach { i ->
            CustomProgressBarView(i / 10f)
        }
    }
}