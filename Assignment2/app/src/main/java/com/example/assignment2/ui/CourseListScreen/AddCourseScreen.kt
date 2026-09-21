package com.example.assignment2.ui.CourseListScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Displays a form for entering a new course's details
 */
@Composable
fun AddCourseScreen(
    onSave: (department: String, courseNumber: String, location: String) -> Unit,
    onCancel: () -> Unit
) {
    var department by remember { mutableStateOf("") }
    var courseNumber by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Add Course")

        OutlinedTextField(
            value = department,
            onValueChange = { department = it },
            label = { Text("Department (e.g. CS)") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = courseNumber,
            onValueChange = { courseNumber = it },
            label = { Text("Course Number (e.g. 4530)") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = location,
            onValueChange = { location = it },
            label = { Text("Location") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = { onSave(department, courseNumber, location) },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Save") }

        Button(
            onClick = onCancel,
            modifier = Modifier.fillMaxWidth()
        ) { Text("Cancel") }
    }
}