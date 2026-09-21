package com.example.assignment2.model

data class Course(
    val department: String,
    val courseNumber: String,
    val location: String
) {
    val name: String
        get() = "$department $courseNumber"
}