package com.example.httpreqdemov2.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
@Entity(
    tableName = "fun_facts",
    indices = [Index(value = ["text"], unique = true)]
)
data class FunFact(
    @PrimaryKey(autoGenerate = true)
    @Transient
    val id: Int = 0,
    val text: String
)
