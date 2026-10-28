package com.nohyam.memorial.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "records")
data class RecordEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0L,
    val question : String,
    val answer : String,
    val createdAt : Long,
    val isRemembered : Boolean? = null
)