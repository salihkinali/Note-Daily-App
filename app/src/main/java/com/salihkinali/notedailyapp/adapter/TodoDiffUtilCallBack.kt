package com.salihkinali.notedailyapp.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.salihkinali.notedailyapp.model.TodoModel

class TodoDiffUtilCallBack : DiffUtil.ItemCallback<TodoModel>() {

    override fun areItemsTheSame(oldItem: TodoModel, newItem: TodoModel): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: TodoModel, newItem: TodoModel): Boolean {
        return oldItem == newItem
    }
}