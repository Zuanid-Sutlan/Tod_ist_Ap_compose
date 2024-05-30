package com.devstudio.todistap.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devstudio.todistap.data.model.Todo

@Database(entities = [Todo::class], version = 1, exportSchema = true)
abstract class TodoDatabase : RoomDatabase(){

    abstract fun todoDao(): TodoDAO


}