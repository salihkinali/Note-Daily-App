package com.salihkinali.notedailyapp.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.salihkinali.notedailyapp.databinding.ItemCardDesignBinding
import com.salihkinali.notedailyapp.model.NoteModel
import com.salihkinali.notedailyapp.view.HomeFragmentDirections
import java.security.AccessController.getContext

class NoteAdapter(private var noteList: List<NoteModel?>) :
    RecyclerView.Adapter<NoteAdapter.CardViewHolder>() {
    class CardViewHolder(val itemCardDesignBinding: ItemCardDesignBinding) :
        RecyclerView.ViewHolder(itemCardDesignBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.CardViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemCardDesignBinding = ItemCardDesignBinding.inflate(layoutInflater, parent, false)
        return NoteAdapter.CardViewHolder(itemCardDesignBinding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val note = noteList[position]
        holder.itemCardDesignBinding.apply {

            note?.let {

                cardItem.setCardBackgroundColor(Color.parseColor("#${note.noteColor}"))
                noteTitleText.text = note.noteTitle
                noteCategoryText.text = note.noteCategory
                noteInsideText.text = note.noteInside

                root.setOnClickListener { button ->
                    note?.let {
                        val action = HomeFragmentDirections.actionHomeToDetailNote(note)
                        Navigation.findNavController(button).navigate(action)
                    }
                }
            }
        }


    }

    override fun getItemCount(): Int = noteList.size
}




