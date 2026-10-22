package com.example.assignment2

import android.app.Application
import androidx.room.Room
import com.example.assignment2.data.CourseDatabase
import com.example.assignment2.data.CourseRepository

/**
 * Where the repository is created
 */
class CourseApplication : Application() {
    private val database: CourseDatabase by lazy {
        Room.databaseBuilder(this, CourseDatabase::class.java, "course_database").build()
    }

    val repository: CourseRepository by lazy {
        CourseRepository(database.courseDao())
    }
}
