package com.example.notes.ui.note

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.notes.R
import com.example.notes.fragmentData.NoteData


class ShowNoteFragment : Fragment() {

    lateinit var noteData: NoteData
    lateinit var navController: NavController
    lateinit var titleView: TextView
    lateinit var textView: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navController = findNavController()
        val view = inflater.inflate(R.layout.fragment_show_note, container, false)
        titleView = view.findViewById(R.id.titleView)
        textView = view.findViewById(R.id.textView)
        noteData = arguments?.getParcelable("noteBundle")!!
        titleView.text = noteData.title
        textView.text = noteData.text
        setHasOptionsMenu(true)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.edit_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_edit) {
            val bundle = bundleOf("noteBundle" to noteData)
            navController.navigate(R.id.action_showNoteFragment_to_editCreateNoteFragment, bundle)
        }
        return super.onOptionsItemSelected(item)
    }
}