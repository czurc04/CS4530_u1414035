package com.example.assignment2.data

import com.example.assignment2.model.Course
import kotlinx.coroutines.flow.Flow

/**
 * Middle layer between the ViewModel and the Room database
 */
class CourseRepository(private val courseDao: CourseDao) {

    val allCourses: Flow<List<Course>> = courseDao.getAllCourses()

    suspend fun addCourse(course: Course) = courseDao.insert(course)

    suspend fun deleteCourse(course: Course) = courseDao.delete(course)
}
