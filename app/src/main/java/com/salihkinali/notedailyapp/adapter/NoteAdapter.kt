package com.salihkinali.notedailyapp.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.salihkinali.notedailyapp.R
import com.salihkinali.notedailyapp.databinding.ItemCardDesignBinding
import com.salihkinali.notedailyapp.model.NoteModel
import com.salihkinali.notedailyapp.view.HomeFragmentDirections
import com.salihkinali.notedailyapp.viewmodel.NoteViewModel


class NoteAdapter(
    private val mContext: Context,
    private val viewModel: NoteViewModel,
    private var noteList: List<NoteModel?>
) :
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
                    val popup = PopupMenu(mContext, popup)
                    popup.menuInflater.inflate(R.menu.popup_menu, popup.menu)
                    popup.setOnMenuItemClickListener { item ->
                        when (item.itemId) {

                            R.id.sil -> {

                                val builder = AlertDialog.Builder(mContext)
                                builder.setTitle(R.string.dialogTitle)
                                builder.setMessage(R.string.dialogMessage)
                                builder.setIcon(android.R.drawable.ic_dialog_alert)

                                builder.setPositiveButton("Yes") { dialogInterface, which ->
                                    viewModel.deleteNote(note)
                                    Toast.makeText(mContext, "Note Deleted", Toast.LENGTH_LONG)
                                        .show()

                                }

                                builder.setNegativeButton("No") { dialogInterface, which ->

                                }
                                val alertDialog: AlertDialog = builder.create()
                                // Set other dialog properties
                                alertDialog.setCancelable(false)
                                alertDialog.show()



                                true
                            }
                            R.id.duzenle -> {
                                note.let {
                                    val action = HomeFragmentDirections.actionHomeToDetailNote(note)
                                    Navigation.findNavController(popUpMenu).navigate(action)
                                }
                                true
                            }
                            else -> false
                        }
                    }
                    popup.show()
                }

                root.setOnLongClickListener { button ->
                    note.let {
                        val action = HomeFragmentDirections.actionHomeToDetailNote(note)
                        Navigation.findNavController(button).navigate(action)
                    }
                    true
                }
            }
        }


    }

    override fun getItemCount(): Int = noteList.size
}




