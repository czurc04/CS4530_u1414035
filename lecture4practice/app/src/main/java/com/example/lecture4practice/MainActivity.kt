package com.example.lecture4practice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.lecture4practice.ui.theme.Lecture4practiceTheme


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TextScreen()
        }
    }
}
@Composable
fun TextScreen() {
    var firstTextBox by remember { mutableStateOf("") }
    var secondTextBox by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column {
        TextField(
            value = firstTextBox,
            onValueChange = { firstTextBox = it },
            label = { Text("First Input:") }
        )
        TextField(
            value = secondTextBox,
            onValueChange = { secondTextBox = it },
            label = { Text("Second Input:") }
        )
        Button(onClick = { result = firstTextBox + secondTextBox }) {
            Text("Concatenate")
        }
        Text(result)
    }
}