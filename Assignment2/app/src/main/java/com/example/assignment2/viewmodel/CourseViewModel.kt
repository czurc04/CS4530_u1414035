package com.example.assignment2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignment2.data.CourseRepository
import com.example.assignment2.model.Course
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * Holds and manages the list of courses, backed by the repository
 */
class CourseViewModel(private val repository: CourseRepository) : ViewModel() {

    val courses: StateFlow<List<Course>> = repository.allCourses
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addCourse(department: String, courseNumber: String, location: String) {
        viewModelScope.launch {
            repository.addCourse(Course(department = department, courseNumber = courseNumber, location = location))
        }
    }

    fun deleteCourse(course: Course) {
        viewModelScope.launch { repository.deleteCourse(course) }
    }
}
