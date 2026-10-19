package com.example.assignment2

import android.app.Application
import com.example.assignment2.data.CourseDatabase
import com.example.assignment2.data.CourseRepository

/**
 * Application class. Repository created here.
 */
class CourseApplication : Application() {
    val repository: CourseRepository by lazy {
        CourseRepository(CourseDatabase.getDatabase(this).courseDao())
    }
}
