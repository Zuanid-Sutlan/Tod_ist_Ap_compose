package com.devstudio.todistap.data.database

import android.content.Context
import androidx.room.Room
import com.devstudio.todistap.data.repository.TodoRepository

object DatabaseGraph {

    private lateinit var database: TodoDatabase

    val repository by lazy { TodoRepository(database.todoDao()) }


    fun databaseProvider(context: Context){
        database = Room.databaseBuilder(context, TodoDatabase::class.java, "todo-database.db").build()
    }

}