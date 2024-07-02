package com.vanshika.recyclerviewapplication

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.vanshika.recyclerviewapplication.databinding.ActivityMainBinding
import com.vanshika.recyclerviewapplication.databinding.CustomDialogBinding

class MainActivity : AppCompatActivity() {
    var binding : ActivityMainBinding ?= null
    lateinit var manager : LinearLayoutManager
    var list = arrayListOf<TaskDataClass>()
    var adapter : TaskRecyclerAdapter = TaskRecyclerAdapter(list)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        manager = LinearLayoutManager(this)
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
                }else{
                    list.add(TaskDataClass(
                        dialogBinding.etTitle.text.toString(),
                        dialogBinding.etDescription.text.toString()
                    ))
                    adapter.notifyDataSetChanged()
                    dialog.dismiss()
                }
            }
        }
    }
}