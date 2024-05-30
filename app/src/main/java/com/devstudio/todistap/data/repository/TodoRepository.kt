package com.devstudio.todistap.data.repository

import com.devstudio.todistap.data.database.TodoDAO
import com.devstudio.todistap.data.model.Todo
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