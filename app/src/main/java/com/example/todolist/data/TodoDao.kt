package com.example.todolist.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

@Query("select * from  myToDos")
fun getAllToDos(): Flow<List<TodoEntity>>

@Insert(onConflict = OnConflictStrategy.REPLACE)
suspend fun insert(todo: TodoEntity)

@Update
suspend fun update(todo: TodoEntity)

@Delete
suspend fun delete(todo: TodoEntity)
}