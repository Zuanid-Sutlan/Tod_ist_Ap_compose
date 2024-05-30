package com.devstudio.todistap.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.devstudio.todistap.data.model.Todo
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TodoDAO {

    // to insert data in the database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insert(todoEntity: Todo)

    // to update data in the database
    @Update
    abstract suspend fun update(todoEntity: Todo)

    // to delete data in the database
    @Delete
    abstract suspend fun delete(todoEntity: Todo)

    // to get all data from database
    @Query("select * from `todo-table`")
    abstract fun getAllTodos(): Flow<List<Todo>>

    // to get data by id from database
    @Query("select * from `todo-table` where id = :id")
    abstract fun getById(id: Long): Flow<Todo>

}