package com.my.todoclean.feature_todo.presentation.todos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.my.todoclean.feature_todo.domain.model.Todo
import com.my.todoclean.feature_todo.domain.use_case.TodoUseCases
import com.my.todoclean.feature_todo.domain.util.OrderType
import com.my.todoclean.feature_todo.domain.util.TodoOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodosViewModel
@Inject constructor(
    private val todoUseCases: TodoUseCases
) : ViewModel() {

    private val _state = MutableLiveData<TodosState>()
    val state: LiveData<TodosState> get() = _state

    private var recentlyDeletedTodo: Todo? = null

    private var getTodosJob: Job? = null

    init {
        getTodos(TodoOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: TodosEvent) {
        when (event) {
            is TodosEvent.Order -> {
                state.value?.let { value ->
                    if (value.todoOrder::class == event.todoOrder::class &&
                        value.todoOrder.orderType == event.todoOrder.orderType
                    ) {
                        return
                    }
                    getTodos(event.todoOrder)
                }

            }

            is TodosEvent.DeleteTodo -> {
                viewModelScope.launch {
                    todoUseCases.deleteTodo(todo = event.todo)
                    recentlyDeletedTodo = event.todo
                }
            }

            is TodosEvent.RestoreTodo -> {
                viewModelScope.launch {
                    todoUseCases.addTodo(recentlyDeletedTodo ?: return@launch)
                    recentlyDeletedTodo = null
                }
            }

            is TodosEvent.ToggleOrderSection -> {
                state.value?.let { value ->
                    _state.value = value.copy(
                        isOrderSectionVisible = !value.isOrderSectionVisible
                    )
                }

            }

            is TodosEvent.UpdateStatus -> {
                viewModelScope.launch {
                    event.todo.isCompleted = !event.todo.isCompleted
                    todoUseCases.updateStatus(todo = event.todo)
                }
            }
        }
    }

    private fun getTodos(todoOrder: TodoOrder) {
        getTodosJob?.cancel()
        getTodosJob = todoUseCases.getTodos(todoOrder).onEach { todos ->
            state.value?.let { value ->
                _state.value = value.copy(
                    todos = todos,
                    todoOrder = todoOrder
                )
            }
        }.launchIn(viewModelScope)
    }

}