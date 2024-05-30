package com.devstudio.todistap.data.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devstudio.todistap.Utils.Prefs
import com.devstudio.todistap.data.database.DatabaseGraph
import com.devstudio.todistap.data.model.Todo
import com.devstudio.todistap.data.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: TodoRepository = DatabaseGraph.repository) :
    ViewModel() {


    // for setting theme...
    private var _changeTheme = mutableStateOf(Prefs.isDarkTheme)
    var theme : State<Boolean> = _changeTheme

    fun setTheme(theme: Boolean){
        _changeTheme.value = theme
    }

    // data base operations

    lateinit var getAll: Flow<List<Todo>>

    init {
        viewModelScope.launch {
            getAll = repository.getAll()
        }
    }

    // insert data in room
    fun insert(todo: Todo) {
        viewModelScope.launch {
            repository.insert(todo)
        }
    }

    // update data in room
    fun update(todo: Todo) {
        viewModelScope.launch {
            repository.update(todo)
        }
    }

    // delete data in room
    fun delete(todo: Todo) {
        viewModelScope.launch {
            repository.delete(todo)
        }
    }

    // get by id from room
    fun getById(id: Long): Flow<Todo> {
        return repository.getById(id)
    }


}