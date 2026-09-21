package com.example.assignment2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.assignment2.ui.CourseListScreen.AddCourseScreen
import com.example.assignment2.ui.CourseListScreen.CourseListScreen
import com.example.assignment2.ui.theme.Assignment2Theme
import com.example.assignment2.viewmodel.CourseViewModel
import androidx.compose.ui.Alignment
import com.example.assignment2.ui.CourseListScreen.CourseDetailScreen
import com.example.assignment2.model.Course
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.safeDrawingPadding

/**
 * Entry point of the app. Hosts single activity & manages which
 * screen is currently shown.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assignment2Theme {
                val viewModel: CourseViewModel = viewModel()
                // tracks whether "add course" page should be shown
                var showAddScreen by remember { mutableStateOf(false) }
                // tracks which course the user tapped into
                var selectedCourse by remember { mutableStateOf<Course?>(null) }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .safeDrawingPadding()
                ) {
                    // decide which screen to display based on current state
                    when {
                        showAddScreen -> {
                            AddCourseScreen(
                                onSave = { department, courseNumber, location ->
                                    viewModel.addCourse(department, courseNumber, location)
                                    showAddScreen = false
                                },
                                onCancel = { showAddScreen = false }
                            )
                        }
                        selectedCourse != null -> {
                            CourseDetailScreen(
                                course = selectedCourse!!,
                                onDelete = {
                                    viewModel.deleteCourse(selectedCourse!!)
                                    selectedCourse = null
                                },
                                onBack = { selectedCourse = null }
                            )
                        }
                        else -> {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Button(
                                    onClick = { showAddScreen = true },
                                    modifier = Modifier.padding(16.dp)
                                ) { Text("Add Course") }

                                CourseListScreen(
                                    courses = viewModel.courses,
                                    onCourseClick = { course -> selectedCourse = course }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}