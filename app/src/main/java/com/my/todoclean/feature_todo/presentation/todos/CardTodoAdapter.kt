package com.my.todoclean.feature_todo.presentation.todos

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.my.todoclean.databinding.CardTodoBinding
import com.my.todoclean.feature_todo.domain.model.Todo

class CardTodoAdapter : RecyclerView.Adapter<CardTodoAdapter.CardTodoViewHolder>() {
    private var _list = ArrayList<Todo>()
    val list get() = _list.toList()

    private lateinit var context: Context

    class CardTodoViewHolder(val binding: CardTodoBinding) : RecyclerView.ViewHolder(binding.root)

    fun setList(newList: ArrayList<Todo>) {
        _list.clear()
        _list.addAll(newList)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): CardTodoViewHolder {
        context = parent.context
        return CardTodoViewHolder(
            CardTodoBinding.inflate(LayoutInflater.from(context), parent, false)
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: CardTodoViewHolder, position: Int) {
        bindCardTodoViewHolder(holder as CardTodoViewHolder, position)
    }

    private fun bindCardTodoViewHolder(holder: CardTodoViewHolder, position: Int) {
        holder.binding.apply {
            val item = list[position]
            tvTitle.text = item.title
            tvDescription.text = item.content
            tvCategory.text = "Business (!)"
            rbIsCompleted.isSelected = item.isCompleted

            rbIsCompleted.setOnCheckedChangeListener { buttonView, isChecked ->
                onClickListenerCustom?.let { listener ->
                    item.id?.let { todoId ->
                        listener(todoId, isChecked)
                    }
                }
            }
        }
    }

    private var onClickListenerCustom: ((todoId: Int, newStatus: Boolean) -> Unit)? = null
    fun setOnClickListenerCustom(f: ((todoId: Int, newStatus: Boolean) -> Unit)) {
        onClickListenerCustom = f
    }

}