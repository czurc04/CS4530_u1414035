package com.example.marbleroll.data

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.example.marbleroll.model.GravityReading
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow

fun SensorManager.gravityFlow(sensor: Sensor, smooth: Boolean): Flow<GravityReading> = channelFlow {
    val alpha = 0.8f
    var fx = 0f
    var fy = 0f

    val listener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            if (smooth) {
                fx = alpha * fx + (1 - alpha) * event.values[0]
                fy = alpha * fy + (1 - alpha) * event.values[1]
            } else {
                fx = event.values[0]
                fy = event.values[1]
            }
            trySend(GravityReading(fx, fy, event.timestamp))
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit
    }

    registerListener(listener, sensor, SensorManager.SENSOR_DELAY_GAME)
    awaitClose { unregisterListener(listener) }
}