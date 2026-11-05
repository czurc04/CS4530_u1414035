package com.example.lecture15

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lecture15.ui.theme.Lecture15Theme
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyViewModel : ViewModel()
{
    private val model = GenerativeModel(
        modelName = "gemini-3.5-flash",
        apiKey = BuildConfig.GEMINI_API_KEY
    )

    private val summaryMutable = MutableStateFlow(" ")
    val summary = summaryMutable.asStateFlow()

    fun summarizeText(input: String){
        viewModelScope.launch{
            try{
                val response = model.generateContent("Summarize this: $input")
                summaryMutable.value = response.text ?: "No summary generated"

            } catch(e: Exception){
                summaryMutable.value = "Error: ${e.message}"
                Log.e("SummarizeError", e.toString())
            }
        }
    }
}

class MainActivity : ComponentActivity() {
    private val viewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lecture15Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SummarizerScreen(
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun SummarizerScreen(viewModel: MyViewModel, modifier: Modifier = Modifier) {
    val summary by viewModel.summary.collectAsState()
    var input by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Text Summarizer", style = MaterialTheme.typography.headlineMedium)

        Text("Original text", style = MaterialTheme.typography.titleMedium)
        OutlinedTextField(
            value = input,
            onValueChange = { input = it },
            modifier = Modifier.fillMaxWidth(),
            minLines = 6,
            placeholder = { Text("Paste the text you want summarized") }
        )

        Button(
            onClick = { viewModel.summarizeText(input) },
            enabled = input.isNotBlank()
        ) {
            Text("Summarize")
        }

        Text("Summary", style = MaterialTheme.typography.titleMedium)
        Text(summary)
    }
}
