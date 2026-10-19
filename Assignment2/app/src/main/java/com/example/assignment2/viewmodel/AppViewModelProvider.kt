package com.example.assignment2.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.assignment2.CourseApplication

/**
 * Factory that builds ViewModels with the app's single repository instance.
 */
object AppViewModelProvider {
    val Factory: ViewModelProvider.Factory = viewModelFactory {
        initializer {
            CourseViewModel(courseApplication().repository)
        }
    }
}

private fun CreationExtras.courseApplication(): CourseApplication =
    this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CourseApplication
