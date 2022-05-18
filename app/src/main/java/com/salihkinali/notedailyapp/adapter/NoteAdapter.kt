package com.salihkinali.notedailyapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import com.salihkinali.notedailyapp.R
import com.salihkinali.notedailyapp.databinding.ItemCardDesignBinding
import com.salihkinali.notedailyapp.model.NoteModel
import com.salihkinali.notedailyapp.view.fragment.HomeFragmentDirections
import com.salihkinali.notedailyapp.view.fragment.NoteDetailFragment


class NoteAdapter(
    private val mContext: Context,

    ) :
    RecyclerView.Adapter<NoteAdapter.CardViewHolder>() {
    class CardViewHolder(val itemCardDesignBinding: ItemCardDesignBinding) :
        RecyclerView.ViewHolder(itemCardDesignBinding.root)

    private val noteList = ArrayList<NoteModel?>()

    var onTodoClick: (NoteModel, Int) -> Unit = { noteModel: NoteModel, i: Int -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemCardDesignBinding = ItemCardDesignBinding.inflate(layoutInflater, parent, false)
        return CardViewHolder(itemCardDesignBinding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val note = noteList[position]
        holder.itemCardDesignBinding.apply {

            note?.let {
                cardItem.setCardBackgroundColor(Color.parseColor(note.noteColor))
                if (note.noteColor == "#282829") {
                    lines.setBackgroundColor(Color.parseColor("#1E1F2C"))
                } else if (note.noteColor == "#007C3F") {
                    lines.setBackgroundColor(Color.parseColor("#1F6628"))
                } else if (note.noteColor == "#F6F54D") {
                    lines.setBackgroundColor(Color.parseColor("#878628"))
                } else if (note.noteColor == "#F55353") {
                    lines.setBackgroundColor(Color.parseColor("#983636"))
                } else {
                    lines.setBackgroundColor(Color.parseColor("#273577"))
                }


                noteTitleText.text = note.noteTitle
                noteCategoryText.text = note.noteCategory
                noteInsideText.text = note.noteInside
                dateTextView.text = note.dateTime
                timeTextView.text = note.timeNow
                if (note.noteColor == "#282829") {
                    noteTitleText.setTextColor(Color.parseColor("#FFFFFF"))
                    noteCategoryText.setTextColor(Color.parseColor("#FFFFFF"))
                    noteInsideText.setTextColor(Color.parseColor("#FFFFFF"))
                    dateTextView.setTextColor(Color.parseColor("#FFFFFF"))
                    timeTextView.setTextColor(Color.parseColor("#FFFFFF"))


                } else {
                    noteTitleText.setTextColor(Color.parseColor("#282829"))
                    noteCategoryText.setTextColor(Color.parseColor("#282829"))
                    noteInsideText.setTextColor(Color.parseColor("#282829"))
                }
                popup.setOnClickListener { popUpMenu ->
                    val popup = PopupMenu(mContext, popUpMenu)
                    popup.inflate(R.menu.popup_menu)
                    popup.setOnMenuItemClickListener {
                        when (it.itemId) {
                            R.id.sil -> {
                                val alertView = AlertDialog.Builder(mContext)
                                alertView.setMessage("Silmek İstediğinizden emin misiniz?")
                                alertView.setTitle("Seçilen Dosya")
                                alertView.setIcon(R.drawable.ic_check)
                                alertView.setPositiveButton("Sil") { dialogInterface, i ->
                                    onTodoClick(note, position)
                                }
                                alertView.setNegativeButton("İptal") { dialogInterface, i ->

                                }
                                alertView.create().show()

                                true
                            }
                            R.id.duzenle -> {
                                val action = HomeFragmentDirections.actionHomeToDetailNote(note)
                                popUpMenu.findNavController().navigate(action)
                                true
                            }
                            else -> false
                        }
                    }
                    popup.show()
                }

                root.setOnLongClickListener { button ->
                    Toast.makeText(mContext, "Dizi Sırası: ${position}", Toast.LENGTH_SHORT).show()
                    true
                }
            }
        }


    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(updatedList: List<NoteModel>) {
        noteList.clear()
        noteList.addAll(updatedList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = noteList.size
}
