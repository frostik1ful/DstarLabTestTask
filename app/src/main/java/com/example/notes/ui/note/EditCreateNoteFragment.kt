package com.example.notes.ui.note

import android.app.Activity
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.notes.R
import com.example.notes.fragmentData.NoteData
import com.example.notes.data.entity.Note
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import java.util.*


class EditCreateNoteFragment : Fragment() {

    lateinit var noteData: NoteData
    lateinit var noteViewModel: NoteViewModel
    lateinit var navController: NavController

    lateinit var textEditView: TextInputEditText
    lateinit var titleEditView: TextInputEditText
    lateinit var thisview: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navController = findNavController()
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_edit_create_note, container, false)
        textEditView = view.findViewById(R.id.textInputView)
        titleEditView = view.findViewById(R.id.titleInputView)
        noteData = arguments?.getParcelable("noteBundle")!!
        titleEditView.setText(noteData.title)
        textEditView.setText(noteData.text)
        thisview = view

        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.save_menu, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val imm: InputMethodManager =
            this.context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(thisview.windowToken, 0)
        if (item.itemId == R.id.menu_save) {
            val title: String = titleEditView.text.toString()
            val text: String = textEditView.text.toString()
            if (checkText(title, text)) {
                noteViewModel.addNote(
                    Note(
                        noteData.id,
                        titleEditView.text.toString(),
                        textEditView.text.toString(),
                        Calendar.getInstance().time
                    )
                )
                navController.navigate(R.id.action_editCreateNoteFragment_to_nav_note)
            } else {
                this.view?.let {
                    Snackbar.make(it, "Please enter title and text", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
                }
            }
        }


        return super.onOptionsItemSelected(item)
    }

    private fun checkText(title: String, text: String): Boolean {
        return title.isNotEmpty() && text.isNotEmpty()
    }
}