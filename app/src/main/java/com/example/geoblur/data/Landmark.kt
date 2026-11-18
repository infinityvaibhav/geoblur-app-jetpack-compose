package com.example.geoblur.data

import kotlinx.serialization.Serializable

@Serializable
data class Landmark(
    val name: String,
    val images: List<String>,
    val options: List<String>
)
