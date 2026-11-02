package com.example.marbleroll.model

// Position (dp) and velocity (dp/s) of the marble
data class MarbleState(
    val x: Float = 0f,
    val y: Float = 0f,
    val vx: Float = 0f,
    val vy: Float = 0f
)