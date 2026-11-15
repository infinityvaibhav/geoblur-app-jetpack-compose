package com.example.geoblur

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.geoblur.ui.theme.GeoBlurTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GeoBlurTheme {
                MainView()
            }
        }
    }
}

@Composable
fun GeoBlurView(modifier: Modifier = Modifier) {
    val isLoading = false
    var currentIndex by remember { mutableIntStateOf(0) }
    val totalLandmark = 10

    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            CustomProgressBarView(
                progress = currentIndex.toFloat() / totalLandmark
            )
            Text("$currentIndex")
            Text(
                text = "Guess the landmark!",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = MaterialTheme.colorScheme.primary,
                    letterSpacing = 1.5.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(16.dp)
            )

            // Middle Section - Image boc
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
                            currentIndex += 1
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

@Composable
fun MainView() {
    GeoBlurView()
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GeoBlurTheme {
        MainView()
    }
}