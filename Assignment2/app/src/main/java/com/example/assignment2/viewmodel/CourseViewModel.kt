package com.example.assignment2.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.assignment2.model.Course

/**
 * Holds and manages the list of courses
 */
class CourseViewModel : ViewModel() {

    val courses = mutableStateListOf<Course>()

    fun addCourse(department: String, courseNumber: String, location: String) {
        courses.add(Course(department, courseNumber, location))
    }

    fun deleteCourse(course: Course) {
        courses.remove(course)
    }
}