package com.example.geoblur.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.geoblur.data.GameUiState
import com.example.geoblur.data.Landmark
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.Dispatcher
import okio.IOException

class GameViewModel: ViewModel() {
    val initialBlurRadius: Float = 20f
    val numberOfTries = 2

    private val _uiState = MutableStateFlow(
        GameUiState(blurRadius = initialBlurRadius)
    )
    val uiState = _uiState.asStateFlow()

    val totalLandmarks: Int = _uiState.value.shuffledLandmark.size

    val currentLandmark: Landmark?
        get() {
            val state = _uiState.value
            val index = state.currentIndex
            val list = state.shuffledLandmark
            Log.d("currentLandmark", "currentLandmark = $index, shuffledLandmark.size = ${list.size}")
            // safety check = valid index and list should not be empty
            return  if(list.isNotEmpty() && index in list.indices) list[index] else null
        }

    private  val json = Json {
        ignoreUnknownKeys = true
    }

    fun loadLandmarksFromAssets(context: Context) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val jsonString = context.assets.open("landmarks.json")
                    .bufferedReader()
                    .use { it.readText() }
                // Parse JSON string to a kotlin list
                val landmarks = json.decodeFromString<List<Landmark>>(jsonString)

                // Shuffle the landmark
                val shuffled = landmarks.shuffled()

                // update the UI state
                _uiState.update {
                    it.copy(
                        shuffledLandmark = shuffled,
                        currentIndex = 0,
                        blurRadius = initialBlurRadius,
                        showDialog = false,
                        loading = false
                    )
                }
                Log.d("loadLandmarksFromAssets", "implemented $landmarks")
            }
        } catch (e: IOException) {
            Log.e("loadLandmarksFromAssets", "Failed to load landmark", e)
        } catch (e: Exception) {
            Log.e("loadLandmarksFromAssets", "Json parsing error", e)
        }
    }
}