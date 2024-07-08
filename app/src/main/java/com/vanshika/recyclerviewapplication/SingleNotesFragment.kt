package com.vanshika.recyclerviewapplication

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.vanshika.recyclerviewapplication.databinding.FragmentSingleNotesBinding
import com.vanshika.recyclerviewapplication.databinding.SinglenotesdialogBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SingleNotesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SingleNotesFragment : Fragment(), SingleNotesInterface {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var binding: FragmentSingleNotesBinding? = null
    var taskDataClass = TaskDataClass()
    lateinit var linearLayoutManager: LinearLayoutManager
    var todoEntity = arrayListOf<TodoEntity>()
    lateinit var todoItemRecycler: TodoItemRecycler
    var todoDatabase: TodoDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSingleNotesBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        todoDatabase = TodoDatabase.getInstance(requireContext())
        todoItemRecycler = TodoItemRecycler(todoEntity, this)
        binding?.rvSingleNotes?.adapter = todoItemRecycler
        arguments?.let {
            var notes = it.getString("notes")
            taskDataClass = Gson().fromJson(notes, TaskDataClass::class.java)
            binding?.etTitle?.setText(taskDataClass.title)
            binding?.etDescription?.setText(taskDataClass.description)
            getList()
        }
        linearLayoutManager = LinearLayoutManager(requireContext())
        binding?.rvSingleNotes?.layoutManager = linearLayoutManager
        binding?.fabSingleNotes?.setOnClickListener {
            Dialog(requireContext()).apply {
                var dialogBinding = SinglenotesdialogBinding.inflate(layoutInflater)
                setContentView(dialogBinding.root)
                dialogBinding.btnAdd.setOnClickListener {
                    if (dialogBinding.etTodo.text.toString().isEmpty()) {
                        dialogBinding.etTodo.error = resources.getString(R.string.enter_text)
                    } else {
                        todoDatabase?.todoDao()?.insertSingleNotes(
                            TodoEntity(
                                taskId = taskDataClass.id,
                                todo = dialogBinding.etTodo.text.toString(),
                                isCompleted = dialogBinding.cbCompleted.isChecked
                            )
                        )
                        todoEntity.clear()
                        getSingleNotes()
                        todoItemRecycler.notifyDataSetChanged()
                        dismiss()
                    }
                }
                getWindow()?.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                show()
            }
        }
        getSingleNotes()
    }

    private fun getList() {
        todoDatabase?.todoDao()?.getList()
    }

    private fun getSingleNotes() {
        todoEntity.clear()
        todoEntity.addAll(
            todoDatabase?.todoDao()?.getSingleNotes(taskId = taskDataClass.id) ?: todoEntity)
        todoItemRecycler.notifyDataSetChanged()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SingleNotesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SingleNotesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun updateSingleNotes(position: Int) {
        var dialogBinding: SinglenotesdialogBinding? = null
        var dialog = Dialog(requireContext())
        dialogBinding = SinglenotesdialogBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialogBinding.btnAdd.setText(resources.getString(R.string.update))
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.show()
        dialogBinding.btnAdd.setOnClickListener {
            if (dialogBinding.etTodo.text.toString().isEmpty()) {
                dialogBinding.etTodo.error = resources.getString(R.string.enter_text)
            } else {
                var isCompleted = dialogBinding.cbCompleted.isChecked
                todoDatabase?.todoDao()?.updateSingleNotes(
                    TodoEntity(
                        id = taskDataClass.id,
                        taskId = todoEntity[position].taskId,
                        todo = dialogBinding.etTodo.text.toString(),
                        isCompleted = isCompleted
                    )
                )
                getSingleNotes()
                todoItemRecycler.notifyDataSetChanged()
                dialog.dismiss()
            }
        }
    }

    override fun deleteSingleNotes(position: Int) {
        var alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle(resources.getString(R.string.are_you_sure_you_want_to_delete_this))
        alertDialog.setPositiveButton(resources.getString(R.string.yes)) { _, _ ->
            todoDatabase?.todoDao()?.deleteSingleNotes(todoEntity[position])
            todoItemRecycler.notifyDataSetChanged()
        }
        alertDialog.setNegativeButton(resources.getString(R.string.no)) { _, _ ->
        }
        getSingleNotes()
        alertDialog.show()
    }
}