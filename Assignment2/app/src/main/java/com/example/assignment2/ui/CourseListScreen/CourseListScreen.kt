package com.example.assignment2.ui.CourseListScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.assignment2.model.Course

/**
 * Displays list of entered courses using lazy column layout
 */
@Composable
fun CourseListScreen(
    courses: List<Course>,
    onCourseClick: (Course) -> Unit
) {
    LazyColumn {
        items(courses) { course ->
            Text(
                text = course.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onCourseClick(course) }
                    .padding(16.dp)
            )
        }
    }
}