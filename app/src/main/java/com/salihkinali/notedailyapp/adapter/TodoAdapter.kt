package com.salihkinali.notedailyapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.salihkinali.notedailyapp.R
import com.salihkinali.notedailyapp.databinding.CardDesignRowBinding
import com.salihkinali.notedailyapp.model.TodoModel

class TodoAdapter : ListAdapter<TodoModel, TodoAdapter.ViewHolder>(TodoDiffUtilCallBack()) {
    var onTodoClick: (Int) -> Unit = { }
    class ViewHolder(val cardDesignRowBinding: CardDesignRowBinding) :
        RecyclerView.ViewHolder(cardDesignRowBinding.root) {

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cardDesignRowBinding =
            CardDesignRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(cardDesignRowBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.cardDesignRowBinding.apply {
            cardview.animation = android.view.animation.AnimationUtils.loadAnimation(holder.cardDesignRowBinding.cardview.context,
            R.anim.recyclerview_animation)
            todoText.text = item.addTodo
            dateText.text = "Belirtilen Zaman Aralığı : ${item.dateTodo}"
            deleteButton.setOnClickListener {
                onTodoClick(item.id)
                Snackbar.make(it,"Yapılacaklar Listesinden Silindi.",2000).show()
            }
        }
    }
}
