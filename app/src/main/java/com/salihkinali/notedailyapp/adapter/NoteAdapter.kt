package com.salihkinali.notedailyapp.adapter

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.salihkinali.notedailyapp.R
import com.salihkinali.notedailyapp.databinding.ItemCardDesignBinding
import com.salihkinali.notedailyapp.model.DataConverters
import com.salihkinali.notedailyapp.model.NoteModel
import com.salihkinali.notedailyapp.view.fragment.HomeFragmentDirections


class NoteAdapter(
    private val mContext: Context,
    ) :
    RecyclerView.Adapter<NoteAdapter.CardViewHolder>() {
    class CardViewHolder(val itemCardDesignBinding: ItemCardDesignBinding) :
        RecyclerView.ViewHolder(itemCardDesignBinding.root)

    private val noteList = ArrayList<NoteModel?>()
    var onNoteClick: (NoteModel) -> Unit = {}

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
                    if (note.noteColor == "#282829") lines.setBackgroundColor(Color.parseColor("#1E1F2C")) else if (note.noteColor == "#007C3F") lines.setBackgroundColor(Color.parseColor("#1F6628")) else if (note.noteColor == "#F6F54D") lines.setBackgroundColor(Color.parseColor("#878628")) else if (note.noteColor == "#F55353") lines.setBackgroundColor(Color.parseColor("#983636")) else lines.setBackgroundColor(Color.parseColor("#273577"))
                    if(note.noteImage != null){
                        lines.visibility = View.GONE
                        noteImage.setImageBitmap(DataConverters().convertBitmap(note.noteImage!!))
                    }else{
                        noteImage.visibility =View.GONE
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
                                alertView.setMessage("Are you sure delete to this note?")
                                alertView.setTitle("Choose File")
                                alertView.setIcon(R.drawable.ic_check)
                                alertView.setPositiveButton("DELETE") { _, _ ->
                                    onNoteClick(note)
                                }
                                alertView.setNegativeButton("CANCEL") { _, _ ->

                                }
                                alertView.create().show()

                                true
                            }
                            R.id.duzenle -> {
                                val action = HomeFragmentDirections.actionHomeToDetailNote(note)
                                popUpMenu.findNavController().navigate(action)
                                val clipboard = cardItem.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val clip: ClipData = ClipData.newPlainText("simple text", "Hello, World!")
                                clipboard.setPrimaryClip(clip)
                                true
                            }
                            R.id.copy -> {
                                val clipboard = cardItem.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                                val clip: ClipData = ClipData.newPlainText("Note Owner", note.noteInside)
                                clipboard.setPrimaryClip(clip)
                                Toast.makeText(mContext,"Copy that",Toast.LENGTH_SHORT).show()
                                true
                            }
                            else -> false
                        }
                    }
                    popup.show()
                }

                root.setOnLongClickListener {
                    Toast.makeText(mContext, "Array Arrange: $position", Toast.LENGTH_SHORT).show()
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

