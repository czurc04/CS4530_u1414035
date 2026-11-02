package com.example.marbleroll

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.view.Surface
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.marbleroll.data.gravityFlow
import com.example.marbleroll.ui.MarbleScreen
import com.example.marbleroll.ui.theme.MarbleRollTheme
import com.example.marbleroll.viewmodel.MarbleViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel: MarbleViewModel by viewModels()

    private lateinit var sensorManager: SensorManager
    private var sensor: Sensor? = null
    private var usingAccelerometer = false
    private var sensorJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val gravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)
        usingAccelerometer = gravity == null
        sensor = gravity ?: sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        setContent {
            MarbleRollTheme {
                MarbleScreen(viewModel)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val s = sensor ?: return
        sensorJob = lifecycleScope.launch {
            sensorManager.gravityFlow(s, smooth = usingAccelerometer).collect { reading ->
                val (sx, sy) = toScreenAxes(reading.x, reading.y)
                viewModel.onGravityChanged(sx, sy, reading.timestampNs)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        sensorJob?.cancel()
        sensorJob = null
        viewModel.onSensorPaused()
    }

    // Convert device axes to screen axes so it works in any orientation
    private fun toScreenAxes(x: Float, y: Float): Pair<Float, Float> {
        @Suppress("DEPRECATION")
        val rotation = windowManager.defaultDisplay.rotation
        return when (rotation) {
            Surface.ROTATION_90 -> Pair(-y, x)
            Surface.ROTATION_180 -> Pair(-x, -y)
            Surface.ROTATION_270 -> Pair(y, -x)
            else -> Pair(x, y)
        }
    }
}