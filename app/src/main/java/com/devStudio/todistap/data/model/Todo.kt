package com.devStudio.todistap.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.devStudio.todistap.data.model.enums.TodoStatus

@Entity(tableName = "todo-table")
data class Todo(

    @ColumnInfo(name = "text")
    val text: String,

    @ColumnInfo(name = "status")
    var status: TodoStatus = TodoStatus.NotDone,

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L
)
