package com.example.vahanassignment.Database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UniversityItem::class], version = 1)
abstract class UniversityDatabase : RoomDatabase() {
    // Abstract function to retrieve the FeedDAO
    abstract fun getFeedDao(): UniversityDAO
}