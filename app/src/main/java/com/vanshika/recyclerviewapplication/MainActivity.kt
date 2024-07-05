package com.vanshika.recyclerviewapplication

import android.app.AlertDialog
import android.app.Dialog
import android.devicelock.DeviceId
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.vanshika.recyclerviewapplication.databinding.ActivityMainBinding
import com.vanshika.recyclerviewapplication.databinding.CustomDialogBinding

class MainActivity : AppCompatActivity(), TaskClickInterface {
    var binding : ActivityMainBinding ?= null
    lateinit var manager : LinearLayoutManager
    var list = arrayListOf<TaskDataClass>()
    var adapter : TaskRecyclerAdapter = TaskRecyclerAdapter(this,list,this)
    lateinit var toDoDatabase : TodoDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        toDoDatabase = TodoDatabase.getInstance(this)
        manager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding?.recyclerView?.layoutManager = manager
        binding?.recyclerView?.adapter = adapter
        binding?.fab?.setOnClickListener {
            var dialog = Dialog(this)
            var dialogBinding = CustomDialogBinding.inflate(layoutInflater)
            dialog.setContentView(dialogBinding.root)
            dialog.getWindow()?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
            dialog.show()
            dialogBinding.btnAdd.setOnClickListener {
                if (dialogBinding.etTitle.text.toString().isEmpty()){
                    dialogBinding.etTitle.error = resources.getString(R.string.enter_title)
                }else if (dialogBinding.etDescription.text.toString().isEmpty()){
                    dialogBinding.etDescription.error = resources.getString(R.string.enter_description)
                }else if (dialogBinding.radioButton.checkedRadioButtonId == -1){
                    Toast.makeText(this, resources.getString(R.string.select_one), Toast.LENGTH_SHORT).show()
                }
                else{
                    var priority = if (dialogBinding.rbLow.isChecked)
                        1
                    else if (dialogBinding.rbMedium.isChecked)
                        2
                    else if (dialogBinding.rbHigh.isChecked)
                        3
                    else
                        0
//                    list.add(TaskDataClass(
//                        title = dialogBinding.etTitle.text.toString(),
//                        description = dialogBinding.etDescription.text.toString(),
//                        priority = priority
//                    ))
                    toDoDatabase.todoDao().insertTodo(
                        TaskDataClass(
                            title = dialogBinding.etTitle.text.toString(),
                            description = dialogBinding.etDescription.text.toString(),
                            priority = priority
                        )
                    )
                    getList()
                    dialog.dismiss()
                }
            }
        }
        getList()
        binding?.rbAllAccPriority?.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){ getList() }
        }
        binding?.rbLowAccPriority?.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                list.clear()
                list.addAll(toDoDatabase.todoDao().changeAccPriority(1))
                adapter.notifyDataSetChanged()
            }
        }
        binding?.rbMediumAccPriority?.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                list.clear()
                list.addAll(toDoDatabase.todoDao().changeAccPriority(2))
                adapter.notifyDataSetChanged()
            }
        }
        binding?.rbHighAccPriority?.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                list.clear()
                list.addAll(toDoDatabase.todoDao().changeAccPriority(3))
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun updateTask(position: Int) {
        Dialog(this).apply {
            var dialogBinding = CustomDialogBinding.inflate(layoutInflater)
            setContentView(dialogBinding.root)
            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            show()
            dialogBinding.etTitle.setText(list[position].title)
            dialogBinding.etDescription.setText(list[position].description)
            when(list[position].priority){
                1-> dialogBinding.rbLow.isChecked = true
                2-> dialogBinding.rbLow.isChecked = true
                3-> dialogBinding.rbLow.isChecked = true
            }
            dialogBinding.btnAdd.setOnClickListener {
                if (dialogBinding.etTitle.text.toString().isEmpty()){
                    dialogBinding.etTitle.error = resources.getString(R.string.enter_title)
                }else if (dialogBinding.etDescription.text.toString().isEmpty()){
                    dialogBinding.etDescription.error = resources.getString(R.string.enter_description)
                }else if (dialogBinding.radioButton.checkedRadioButtonId == -1){
                    Toast.makeText(this@MainActivity, resources.getString(R.string.select_one), Toast.LENGTH_SHORT).show()
                }else {
                    var priority = if (dialogBinding.rbLow.isChecked) {
                        1
                    } else if (dialogBinding.rbMedium.isChecked) {
                        2
                    } else if (dialogBinding.rbHigh.isChecked) {
                        3
                    } else {
                        0
                    }
//                    list.set(
//                        position, TaskDataClass(
//                            title = dialogBinding.etTitle.text.toString(),
//                            description = dialogBinding.etDescription.text.toString(),
//                            priority = priority
//                        )
//                    )

                    toDoDatabase.todoDao().updateList(TaskDataClass(
                        id = list[position].id,
                        title = dialogBinding.etTitle.text.toString(),
                        description = dialogBinding.etDescription.text.toString(),
                        priority = priority
                    ))
                    adapter.notifyDataSetChanged()
                    getList()
                    dismiss()
                }
            }
        }
    }

    override fun deleteTask(position: Int) {
        var alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(resources.getString(R.string.are_you_sure_you_want_to_delete_this))
        alertDialog.setPositiveButton(resources.getString(R.string.yes)){_,_ ->
//            list.removeAt(position)
            toDoDatabase.todoDao().deleteList(list[position])
            adapter.notifyDataSetChanged()
        }
        alertDialog.setNegativeButton(resources.getString(R.string.no)){_,_ ->
        }
        getList()
        alertDialog.show()
    }
    fun getList(){
        list.clear()
        list.addAll(toDoDatabase.todoDao().getList())
        adapter.notifyDataSetChanged()
    }
}
