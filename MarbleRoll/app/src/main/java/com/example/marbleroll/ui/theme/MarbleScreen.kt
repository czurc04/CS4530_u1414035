package com.example.marbleroll.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.marbleroll.viewmodel.MarbleViewModel

@Composable
fun MarbleScreen(viewModel: MarbleViewModel) {
    val state by viewModel.state.collectAsState()
    val marbleSize = 40.dp

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1E1E1E))
    ) {
        val maxX = (maxWidth - marbleSize).value
        val maxY = (maxHeight - marbleSize).value

        LaunchedEffect(maxX, maxY) {
            viewModel.setBounds(maxX, maxY)
        }

        Box(
            modifier = Modifier
                .offset(x = state.x.dp, y = state.y.dp)
                .size(marbleSize)
                .background(Color.Cyan, CircleShape)
        )
    }
}