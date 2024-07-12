package com.devStudio.todistap.data.repository

import com.devStudio.todistap.data.database.TodoDAO
import com.devStudio.todistap.data.model.Todo
import kotlinx.coroutines.flow.Flow

class TodoRepository(private val todoDAO: TodoDAO) {


    suspend fun insert(todo: Todo) {
        todoDAO.insert(todo)
    }

    suspend fun update(todo: Todo) {
        todoDAO.update(todo)
    }

    suspend fun delete(todo: Todo) {
        todoDAO.delete(todo)
    }

    fun getAll(): Flow<List<Todo>> {
        return todoDAO.getAllTodos()
    }

    fun getById(id: Long): Flow<Todo> {
        return todoDAO.getById(id)
    }

}