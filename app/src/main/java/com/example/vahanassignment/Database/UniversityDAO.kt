package com.example.vahanassignment.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface UniversityDAO {
    // Upsert annotation to insert or update a list of FeedItem entities
    @Upsert
    suspend fun insertall(Universitylist: List<UniversityItem>)

    // Delete annotation to delete a single FeedItem entity
    @Delete
    suspend fun delete(entities: UniversityItem)

    // Query annotation to fetch all FeedItem entities from the Feed table sorted by publish date
    @Query("SELECT * FROM University")
    suspend fun getUniversityList(): List<UniversityItem>
}