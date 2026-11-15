package com.example.geoblur.data

data class GameUiState(
    val shuffledLandmark: List<Landmark> = emptyList(),
    val currentIndex: Int = 0,
    val blurRadius: Float = 0f,
    val showDialog: Boolean = false,
    val loading: Boolean = true
)