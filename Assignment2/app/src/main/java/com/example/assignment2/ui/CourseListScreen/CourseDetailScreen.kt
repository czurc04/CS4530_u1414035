package com.example.assignment2.ui.CourseListScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.assignment2.model.Course

/**
 * Displays page for showing details for a course when a user clicks on
 * a course
 */
@Composable
fun CourseDetailScreen(
    course: Course,
    onDelete: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Department: ${course.department}")
        Text("Course Number: ${course.courseNumber}")
        Text("Location: ${course.location}")

        Button(onClick = onDelete, modifier = Modifier.fillMaxWidth()) {
            Text("Delete Course")
        }
        Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
            Text("Back")
        }
    }
}