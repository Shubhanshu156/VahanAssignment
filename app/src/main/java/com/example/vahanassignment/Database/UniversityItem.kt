package com.example.vahanassignment.Database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "University")
data class UniversityItem(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val country: String,
    val name: String,
    val web_pages: String
)
