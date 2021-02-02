package com.example.notes.ui.note

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.adapter.NoteAdapter
import com.example.notes.fragmentData.NoteData
import com.example.notes.data.entity.Note
import com.google.android.material.floatingactionbutton.FloatingActionButton


class NoteFragment : Fragment() {
    private lateinit var recyclerViewNotes: RecyclerView
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var noteViewModel: NoteViewModel
    lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_of_notes, container, false)
        noteViewModel = ViewModelProvider(this).get(NoteViewModel::class.java)
        loadData()
        recyclerViewNotes = view.findViewById(R.id.recyclerViewNotes)
        noteAdapter = NoteAdapter()
        recyclerViewNotes.layoutManager = LinearLayoutManager(view.context)
        recyclerViewNotes.adapter = noteAdapter
        setHasOptionsMenu(true)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val createButton: FloatingActionButton = view.findViewById(R.id.createNewNoteButton)
        val deleteButton: FloatingActionButton = view.findViewById(R.id.deleteNoteButton)
        var selectorActive = false
        navController = findNavController()
        createButton.setOnClickListener {
            val bundle = bundleOf("noteBundle" to NoteData(0,"", ""))
            navController.navigate(R.id.action_nav_note_to_editCreateNoteFragment,bundle)
        }
        deleteButton.setOnClickListener {
            noteAdapter.notesIdToDelete.forEach { id ->
                noteViewModel.deleteNoteById(id)
            }
            noteAdapter.notesIdToDelete.clear()
            noteAdapter.checkBoxStateArray.clear()
            noteAdapter.selectingNotes = false
            selectorActive = false
            noteAdapter.noteViews.forEach {
                val checkBox = it.findViewById<CheckBox>(R.id.noteSelectCheckBox)
                checkBox.isChecked = false
                checkBox.visibility = View.INVISIBLE
            }
            it.visibility = View.INVISIBLE
            createButton.visibility = View.VISIBLE
        }

        noteAdapter.onNoteClickListener = object : NoteAdapter.OnNoteClickListener {
            override fun onNoteClick(noteView: View, noteId: Int, position: Int) {
                if (noteAdapter.selectingNotes && !selectorActive) {
                    selectorActive = true
                } else if (selectorActive) {
                    selectNote(noteId, position)
                } else {
                    val title = noteView.findViewById<TextView>(R.id.titleView).text.toString()
                    val text = noteView.findViewById<TextView>(R.id.textView).text.toString()
                    val bundle = bundleOf("noteBundle" to NoteData(noteId,title, text))
                    navController.navigate(R.id.action_nav_note_to_showNoteFragment, bundle)
                }
            }

            override fun onNoteLongClick(noteView: View, noteId: Int, position: Int) {
                noteAdapter.selectingNotes = true
                deleteButton.visibility = View.VISIBLE
                createButton.visibility = View.INVISIBLE
                noteAdapter.noteViews.forEach {
                    it.findViewById<CheckBox>(R.id.noteSelectCheckBox).visibility = View.VISIBLE
                }
                selectNote(noteId, position)
            }

        }
    }

    fun selectNote(noteId: Int, position: Int) {
        val checkBox =
            noteAdapter.noteViews[position].findViewById<CheckBox>(R.id.noteSelectCheckBox)
        checkBox.toggle()
        noteAdapter.checkBoxStateArray.put(position, checkBox.isChecked)
        if (checkBox.isChecked) {
            noteAdapter.notesIdToDelete.add(noteId)
        } else {
            noteAdapter.notesIdToDelete.remove(noteId)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.sort_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.sortByTitle -> sortNotesByTitle()
            R.id.sortByDate -> sortNotesByDate()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadData() {
        val data = noteViewModel.allData
        subscribeAdapterOnData(data)

    }

    private fun sortNotesByDate() {
        val data = noteViewModel.getAllNotesSortedByDate()
        subscribeAdapterOnData(data)
    }

    private fun sortNotesByTitle() {
        val data = noteViewModel.getAllNotesSortedByTitle()
        subscribeAdapterOnData(data)
    }

    private fun subscribeAdapterOnData(liveData: LiveData<List<Note>>) {
        liveData.observe(viewLifecycleOwner, {
            noteAdapter.notesList.clear()
            noteAdapter.notesList.addAll(it)
            noteAdapter.notifyDataSetChanged()
        })
    }
}