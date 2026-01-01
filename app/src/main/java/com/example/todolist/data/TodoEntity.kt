package com.example.todolist.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "myToDos")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int =0,
    val title: String,
    val isDone: Boolean =false
)
