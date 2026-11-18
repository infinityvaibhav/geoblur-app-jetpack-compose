package com.example.geoblur.view

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import com.example.geoblur.data.GameUiState
import com.example.geoblur.viewmodel.GameViewModel

@Composable
fun GeoBlurContentView(
    modifier: Modifier = Modifier,
    viewModel: GameViewModel,
    uiState: GameUiState,
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
        var imageLoading by remember { mutableStateOf(true) }
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

            val imageUrl = currentLandmark.images.firstOrNull()
            Log.d("imageUrl", imageUrl.toString())
            // Middle Section - Image box
            Box(
                modifier = modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Landmark Image",
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .aspectRatio(1f)
                        .clip(MaterialTheme.shapes.medium)
                        .blur(uiState.blurRadius.dp),
                    onState = {
                        // Check if image is currently loading
                        imageLoading = it is AsyncImagePainter.State.Loading
                    }
                )

                if (imageLoading) {
                    CircularProgressIndicator()
                }
            }

            Spacer(Modifier.height(16.dp))

            // Bottom Section - Buttons
            Column {

                val shuffledOption = remember(currentLandmark) {
                    currentLandmark.options.shuffled()
                }

                shuffledOption.forEach { option ->
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

            val playerWon = viewModel.playerWon

            if (uiState.showDialog) {
                AlertDialog(
                    onDismissRequest = {},
                    confirmButton = {
                        Button(
                            onClick = {
                                viewModel.restartGame()
                            }
                        ) {
                            Text("Play Again")
                        }
                    },
                    title = {
                        Text(if (playerWon) "Congrats!" else "Game Over")
                    },
                    text = {
                        Text(
                            if (playerWon)
                                "You have marked all landmarks correctly"
                            else
                                "Better Luck next time"
                        )
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GeoBlurContentPreview() {
    GeoBlurContentView(
        viewModel = GameViewModel(),
        uiState = GameUiState(),
        innerPadding = PaddingValues(10.dp)
    )
}