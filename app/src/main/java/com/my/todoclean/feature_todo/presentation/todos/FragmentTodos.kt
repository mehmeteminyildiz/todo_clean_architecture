package com.my.todoclean.feature_todo.presentation.todos

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.my.todoclean.databinding.FragmentTodosBinding
import com.my.todoclean.feature_todo.domain.model.Todo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentTodos : Fragment() {

    private var _binding: FragmentTodosBinding? = null
    private val binding: FragmentTodosBinding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTodosBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        handleClickEvents()
        loadTodos()
    }


    private fun loadTodos() {
        val todoList = ArrayList<Todo>()
        for (i in 1..100) {
            todoList.add(
                Todo(
                    "TitleTitleTitleTitleTitleTitle-$i",
                    "Content-$i is gonna be here if I want :D",
                    System.currentTimeMillis(),
                    Color.RED,
                    false,
                    1
                )
            )
        }
        processTodoList(todoList)
    }

    private fun processTodoList(todoList: ArrayList<Todo>) {
        binding.apply {

            if (todoList.isNotEmpty()) {
                val todoAdapter = CardTodoAdapter()
                rvTodos.adapter = todoAdapter
                rvTodos.setHasFixedSize(true)
                todoAdapter.setList(todoList)
                todoAdapter.setOnClickListenerCustom { todoId, newStatus ->
                    Log.e("TAG", "id : $todoId, newStatus : $newStatus")
                }
            }
        }
    }

    private fun handleClickEvents() {
        binding.apply {

        }
    }

    private fun initialize() {

    }

}