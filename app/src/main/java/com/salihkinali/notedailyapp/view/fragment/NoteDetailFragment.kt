package com.salihkinali.notedailyapp.view.fragment


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.salihkinali.notedailyapp.R
import com.salihkinali.notedailyapp.databese.NoteDatabese
import com.salihkinali.notedailyapp.databinding.FragmentNoteDetailBinding
import com.salihkinali.notedailyapp.model.Constants
import com.salihkinali.notedailyapp.model.DataConverters
import com.salihkinali.notedailyapp.model.NoteModel
import com.salihkinali.notedailyapp.viewmodel.NoteViewModel
import com.salihkinali.notedailyapp.viewmodel.NoteViewModelFactory
import java.io.FileDescriptor
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class NoteDetailFragment : Fragment() {
    private var _binding: FragmentNoteDetailBinding? = null
    private val binding get() = _binding!!
    private val args: NoteDetailFragmentArgs by navArgs()
    private lateinit var selectedNoteColor: String
    private lateinit var selectedRadioState: String
    private lateinit var notes: NoteModel
    private var tempBitmap: Bitmap? = null
    private lateinit var db: NoteDatabese
    private lateinit var getPermissionResult: ActivityResultLauncher<String>
    private lateinit var getImageResult: ActivityResultLauncher<String>
    private lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = NoteDatabese.getInstance(requireContext())!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteDetailBinding.inflate(inflater, container, false)
        notes = args.note
        selectedNoteColor = notes.noteColor
        selectedRadioState = notes.noteCategory
        val application = requireNotNull(this.activity).application

        val dbDao = NoteDatabese.getInstance(application)?.noteDatabeseDao

        val viewModelFactory = dbDao?.let { NoteViewModelFactory(it, application) }

        viewModel = viewModelFactory?.let {

            ViewModelProvider(this, it)[NoteViewModel::class.java]
        }!!
        getPermissionResult =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    getImageResult.launch("image/*")
                } else {
                    return@registerForActivityResult
                }
            }
        getImageResult = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                binding.realImage.visibility = View.VISIBLE
                binding.selectImage.visibility = View.GONE
                tempBitmap = getBitmapFromUri(uri)
                binding.realImage.setImageBitmap(tempBitmap)
            }
        }
        return binding.root
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            binding.selectImage.setOnClickListener {
                getPermissionResult.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            noteTitle.setText(notes.noteTitle)
            when (notes.noteCategory) {
                Constants.EDUCATION -> education.isChecked = true
                Constants.LIFE -> life.isChecked = true
                Constants.FUN -> `fun`.isChecked = true
                else -> another.isChecked = true
            }
            when (notes.noteColor) {

                Constants.BLACK_COLOR -> imageColor1.setImageResource(R.drawable.ic_check)
                Constants.GREEN_COLOR -> imageColor2.setImageResource(R.drawable.ic_check)
                Constants.YELLOW_COLOR -> imageColor3.setImageResource(R.drawable.ic_check)
                Constants.BLUE_COLOR -> imageColor4.setImageResource(R.drawable.ic_check)
                else -> imageColor5.setImageResource(R.drawable.ic_check)
            }
            note.setText(notes.noteInside)
            if (notes.noteImage != null) {
                realImage.visibility = View.VISIBLE
                selectImage.visibility = View.GONE
                realImage.setImageBitmap(DataConverters().convertBitmap(notes.noteImage!!))
            } else {
                realImage.visibility = View.GONE
                selectImage.visibility = View.VISIBLE
            }
            binding.selectImage.setOnClickListener {
                getPermissionResult.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }
            viewColor1.setOnClickListener {
                selectedNoteColor = Constants.BLACK_COLOR
                imageColor1.setImageResource(R.drawable.ic_check)
                imageColor2.setImageResource(0)
                imageColor3.setImageResource(0)
                imageColor4.setImageResource(0)
                imageColor5.setImageResource(0)
            }
            viewColor2.setOnClickListener {
                selectedNoteColor = Constants.GREEN_COLOR
                imageColor1.setImageResource(0)
                imageColor2.setImageResource(R.drawable.ic_check)
                imageColor3.setImageResource(0)
                imageColor4.setImageResource(0)
                imageColor5.setImageResource(0)
            }
            viewColor3.setOnClickListener {
                selectedNoteColor = Constants.YELLOW_COLOR
                imageColor1.setImageResource(0)
                imageColor2.setImageResource(0)
                imageColor3.setImageResource(R.drawable.ic_check)
                imageColor4.setImageResource(0)
                imageColor5.setImageResource(0)
            }
            viewColor4.setOnClickListener {
                selectedNoteColor = Constants.BLUE_COLOR
                imageColor1.setImageResource(0)
                imageColor2.setImageResource(0)
                imageColor3.setImageResource(0)
                imageColor4.setImageResource(R.drawable.ic_check)
                imageColor5.setImageResource(0)
            }
            viewColor5.setOnClickListener {
                selectedNoteColor = Constants.PINK_COLOR
                imageColor1.setImageResource(0)
                imageColor2.setImageResource(0)
                imageColor3.setImageResource(0)
                imageColor4.setImageResource(0)
                imageColor5.setImageResource(R.drawable.ic_check)
            }
            radioGroup.setOnCheckedChangeListener { _, checkedId ->
                selectedRadioState = when (checkedId) {
                    R.id.education -> Constants.EDUCATION
                    R.id.life -> Constants.LIFE
                    R.id.`fun` -> Constants.FUN
                    else -> Constants.ANOTHER
                }
            }
            val current = LocalDateTime.now()
            val formatterDate = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            val formatterTime = DateTimeFormatter.ofPattern("HH:mm")
            val formattedDate = current.format(formatterDate)
            val formattedTime = current.format(formatterTime)
            updateButton.setOnClickListener {

                val title = noteTitle.text.toString()
                val inside = note.text.toString()
                val noteImage = notes.noteImage

                if (title.isNotEmpty() && inside.isNotEmpty()) {
                    notes.noteTitle = title
                    notes.noteCategory = selectedRadioState
                    notes.noteImage = if(tempBitmap!=null){DataConverters().convertByteArray(tempBitmap!!)}
                    else{noteImage}
                    notes.noteColor = selectedNoteColor
                    notes.noteInside = inside
                    notes.dateTime = formattedDate
                    notes.timeNow = formattedTime
                    viewModel.updateNote(notes)
                    val action = NoteDetailFragmentDirections.actionDetailToHomeFragment()
                    findNavController().navigate(action)

                } else {
                    Snackbar.make(it, "Please Fill in the blanks!", Snackbar.LENGTH_LONG)
                        .show()
                }

            }


        }


    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getBitmapFromUri(uri: Uri): Bitmap? {
        val parcelFileDescriptor = context!!.contentResolver.openFileDescriptor(uri, "r")
        val fileDescriptor: FileDescriptor = parcelFileDescriptor!!.fileDescriptor
        val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
        parcelFileDescriptor.close()
        return image
    }
}
