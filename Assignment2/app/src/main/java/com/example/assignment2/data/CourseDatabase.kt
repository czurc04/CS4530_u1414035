package com.example.assignment2.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.assignment2.model.Course

/**
 * The Room database
 */
@Database(entities = [Course::class], version = 1, exportSchema = false)
abstract class CourseDatabase : RoomDatabase() {
    abstract fun courseDao(): CourseDao
}
