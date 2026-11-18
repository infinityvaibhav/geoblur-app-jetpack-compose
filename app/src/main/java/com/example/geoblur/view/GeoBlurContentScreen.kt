package com.example.geoblur.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.geoblur.viewmodel.GameViewModel

@Composable
fun GeoBlurContentScreen(
    modifier: Modifier = Modifier,
    viewModel: GameViewModel,
    innerPadding: PaddingValues) {
    val currentLandmark = viewModel.currentLandmark

    if (currentLandmark == null) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Bo Landmark to show")
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            CustomProgressBarView(
                progress = viewModel.progress
            )
            Text(
                text = "Guess the landmark!",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = MaterialTheme.colorScheme.primary,
                    letterSpacing = 1.5.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = modifier.padding(16.dp)
            )

            // Middle Section - Image box
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(MaterialTheme.shapes.medium)
                    .background(Color.Red),
                contentAlignment = Alignment.Center
            ) {
                Text("Image goes here")
            }

            // Bottom Section - Buttons
            Column {
                val options = listOf(
                    "Acropolis",
                    "Parthenon",
                    "Pantheon",
                    "Colosseum"
                )

                options.forEach { option ->
                    Button(
                        onClick = {
                            viewModel.submitAnswer(option)
                        },
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text(option)
                    }
                }
            }
        }
    }
}