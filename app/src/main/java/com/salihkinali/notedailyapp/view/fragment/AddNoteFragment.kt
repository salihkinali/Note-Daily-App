package com.salihkinali.notedailyapp.view.fragment

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.salihkinali.notedailyapp.R
import com.salihkinali.notedailyapp.databese.NoteDatabese
import com.salihkinali.notedailyapp.databinding.FragmentAddNoteBinding
import com.salihkinali.notedailyapp.model.DataConverters
import com.salihkinali.notedailyapp.model.NoteModel
import com.salihkinali.notedailyapp.viewmodel.AddNoteViewModel
import com.salihkinali.notedailyapp.viewmodel.AddNoteViewModelFactory
import java.io.FileDescriptor
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class AddNoteFragment : Fragment() {
    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!
    private lateinit var db: NoteDatabese
    private lateinit var viewModel: AddNoteViewModel
    private var tempBitmap: Bitmap? = null
    private lateinit var getPermissionResult: ActivityResultLauncher<String>
    private lateinit var getImageResult: ActivityResultLauncher<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = NoteDatabese.getInstance(requireContext())!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)

        val dbDao = NoteDatabese.getInstance(requireContext())?.noteDatabeseDao

        val viewModelFactory = dbDao?.let {noteDao ->
            AddNoteViewModelFactory(noteDao) }

        viewModel = viewModelFactory?.let {

            ViewModelProvider(this, it)[AddNoteViewModel::class.java]
        }!!
        getPermissionResult = registerForActivityResult(ActivityResultContracts.RequestPermission()){isGranted ->
            if(isGranted){
               getImageResult.launch("image/*")
            }else{
                return@registerForActivityResult
            }
        }
        getImageResult = registerForActivityResult(ActivityResultContracts.GetContent()){uri ->
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

            viewColor1.setOnClickListener {

                viewModel.chooseOne()
                imageColor1.setImageResource(R.drawable.ic_check)
                imageColor2.setImageResource(0)
                imageColor3.setImageResource(0)
                imageColor4.setImageResource(0)
                imageColor5.setImageResource(0)
            }
            viewColor2.setOnClickListener {

                viewModel.choseTwo()
                imageColor1.setImageResource(0)
                imageColor2.setImageResource(R.drawable.ic_check)
                imageColor3.setImageResource(0)
                imageColor4.setImageResource(0)
                imageColor5.setImageResource(0)
            }
            viewColor3.setOnClickListener {
                viewModel.chooseThree()
                imageColor1.setImageResource(0)
                imageColor2.setImageResource(0)
                imageColor3.setImageResource(R.drawable.ic_check)
                imageColor4.setImageResource(0)
                imageColor5.setImageResource(0)
            }
            viewColor4.setOnClickListener {
                viewModel.chooseFour()
                imageColor1.setImageResource(0)
                imageColor2.setImageResource(0)
                imageColor3.setImageResource(0)
                imageColor4.setImageResource(R.drawable.ic_check)
                imageColor5.setImageResource(0)
            }
            viewColor5.setOnClickListener {
                viewModel.chooseFive()
                imageColor1.setImageResource(0)
                imageColor2.setImageResource(0)
                imageColor3.setImageResource(0)
                imageColor4.setImageResource(0)
                imageColor5.setImageResource(R.drawable.ic_check)
            }

            radioGroup.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.education -> viewModel.selectONe()
                    R.id.life -> viewModel.selectTwo()
                    R.id.`fun` -> viewModel.selectThree()
                    else -> viewModel.selectFour()
                }
            }
            binding.selectImage.setOnClickListener{

                // on below line we are creating a new bottom sheet dialog.
                val dialog = BottomSheetDialog(requireContext())

                // on below line we are inflating a layout file which we have created.
                val view = layoutInflater.inflate(R.layout.bottom_sheet, null)

                // on below line we are creating a variable for our button
                // which we are using to dismiss our dialog.
                val btnChoose = view.findViewById<Button>(R.id.chooseImage)
                val galleryImage = view.findViewById<ImageView>(R.id.galleryImage)
                // on below line we are adding on click listener
                // for our dismissing the dialog button.
                galleryImage.setOnClickListener{
                    dialog.dismiss()
                    getPermissionResult.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                }
                btnChoose.setOnClickListener {
                    dialog.dismiss()
                }
                // below line is use to set cancelable to avoid
                // closing of dialog box when clicking on the screen.
                dialog.setCancelable(false)

                // on below line we are setting
                // content view to our view.
                dialog.setContentView(view)

                // on below line we are calling
                // a show method to display a dialog.
                dialog.show()

            }
            addButton.setOnClickListener {
                val title = noteTitle.text.toString()
                val inside = note.text.toString()
                saveNote(title,inside)
            }
        }
    }

    private fun saveNote(title:String,inside:String) {

        val current = LocalDateTime.now()
        val formatterDate = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val formatterTime = DateTimeFormatter.ofPattern("HH:mm")
        val formattedDate = current.format(formatterDate)
        val formattedTime = current.format(formatterTime)

        if (title.isNotEmpty() &&  inside.isNotEmpty()) {
            viewModel.addNote((
                    NoteModel(
                        noteTitle = title,
                        noteCategory = viewModel.selectedRadioState,
                        noteImage = if(tempBitmap!=null){DataConverters().convertByteArray(tempBitmap!!)}
                        else{ null},
                        noteInside = inside,
                        noteColor = viewModel.selectedNoteColor,
                        timeNow = formattedTime,
                        dateTime = formattedDate
                    )
                    ))
            val action = AddNoteFragmentDirections.addNoteToHomeFragment()
            findNavController().navigate(action)
        } else {
            Toast.makeText(context, "Please Fill in the Blanks!", Toast.LENGTH_LONG)
                .show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    @Throws(IOException::class)
    private fun getBitmapFromUri(uri: Uri): Bitmap? {
        val parcelFileDescriptor = context!!.contentResolver.openFileDescriptor(uri, "r")
        val fileDescriptor: FileDescriptor = parcelFileDescriptor!!.fileDescriptor
        val image = BitmapFactory.decodeFileDescriptor(fileDescriptor)
        parcelFileDescriptor.close()
        return image
    }
}