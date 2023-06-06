package com.my.todoclean.feature_todo.presentation.add_edit_todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.my.todoclean.databinding.FragmentAddEditTodoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentAddEditTodoFragment : Fragment() {

    private var _binding: FragmentAddEditTodoBinding? = null
    val binding: FragmentAddEditTodoBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddEditTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        handleClickEvents()
    }

    private fun initialize() {

    }

    private fun handleClickEvents() {

    }

}