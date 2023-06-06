package com.my.todoclean.feature_todo.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    var isCompleted: Boolean,
    @PrimaryKey val id: Int? = null
)

class InvalidTodoException(message: String) : Exception(message)
