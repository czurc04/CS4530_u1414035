package com.example.marbleroll.viewmodel

import androidx.lifecycle.ViewModel
import com.example.marbleroll.model.MarbleState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.exp

class MarbleViewModel : ViewModel() {

    private val _state = MutableStateFlow(MarbleState())
    val state: StateFlow<MarbleState> = _state.asStateFlow()

    private var maxX = 0f
    private var maxY = 0f
    private var boundsSet = false
    private var lastTimestampNs = 0L

    companion object {
        private const val SCALE = 80f
        private const val FRICTION = 1.5f
        private const val BOUNCE = 0.4f
    }

    fun setBounds(maxX: Float, maxY: Float) {
        this.maxX = maxX
        this.maxY = maxY
        if (!boundsSet) {
            boundsSet = true
            _state.value = MarbleState(x = maxX / 2f, y = maxY / 2f) // start centered
        } else {
            // keep marble inside if the size changed
            _state.update { it.copy(x = it.x.coerceIn(0f, maxX), y = it.y.coerceIn(0f, maxY)) }
        }
    }

    // gx, gy = gravity components along the device's x and y axes (m/s^2)
    fun onGravityChanged(gx: Float, gy: Float, timestampNs: Long) {
        if (lastTimestampNs == 0L) {
            lastTimestampNs = timestampNs
            return
        }
        val dt = ((timestampNs - lastTimestampNs) / 1_000_000_000f).coerceAtMost(0.05f)
        lastTimestampNs = timestampNs

        val ax = -gx
        val ay = gy
        val damping = exp(-FRICTION * dt) // friction

        _state.update { s ->
            var vx = (s.vx + dt * SCALE * ax) * damping
            var vy = (s.vy + dt * SCALE * ay) * damping
            var x = s.x + dt * vx
            var y = s.y + dt * vy

            // keep inside the screen, with a little bounce
            if (x < 0f) { x = 0f; vx = -vx * BOUNCE }
            if (x > maxX) { x = maxX; vx = -vx * BOUNCE }
            if (y < 0f) { y = 0f; vy = -vy * BOUNCE }
            if (y > maxY) { y = maxY; vy = -vy * BOUNCE }

            MarbleState(x, y, vx, vy)
        }
    }

    // Call when the sensor is unregistered so the first dt after resume isn't huge
    fun onSensorPaused() {
        lastTimestampNs = 0L
    }
}