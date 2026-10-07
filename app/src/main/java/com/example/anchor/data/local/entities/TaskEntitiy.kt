package com.example.anchor.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "tasks")
data class TaskEntitiy(
    @PrimaryKey
    val id:String= UUID.randomUUID().toString(),
    val title:String,
    val isCompleted:Boolean=false
)
