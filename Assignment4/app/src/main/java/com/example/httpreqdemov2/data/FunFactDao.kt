package com.example.httpreqdemov2.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FunFactDao {

    @Query("SELECT * FROM fun_facts WHERE text LIKE '%' || :query || '%' ORDER BY id DESC")
    fun search(query: String): Flow<List<FunFact>>

    // Returns the new row id, or -1 if the fact was already saved
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(fact: FunFact): Long
}
