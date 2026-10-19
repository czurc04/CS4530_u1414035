package com.example.assignment2.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.assignment2.model.Course
import kotlinx.coroutines.flow.Flow

/**
 * Database operations for courses
 */
@Dao
interface CourseDao {
    @Query("SELECT * FROM courses ORDER BY department, courseNumber")
    fun getAllCourses(): Flow<List<Course>>

    @Insert
    suspend fun insert(course: Course)

    @Delete
    suspend fun delete(course: Course)
}
