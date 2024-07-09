package com.vanshika.recyclerviewapplication

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CompoundButton
import android.widget.RadioButton
import androidx.core.content.ContextCompat
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Calendar

class TaskRecyclerAdapter(var context : Context, var list : ArrayList<TaskShownList>, var taskClickInterface: TaskClickInterface) : RecyclerView.Adapter<TaskRecyclerAdapter.ViewHolder>() { //to connect with foreign key change lis
    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var tvTitle :TextView = view.findViewById(R.id.tvTitle)
        var tvDescription : TextView = view.findViewById(R.id.tvDescription)
        var tvDate : TextView = view.findViewById(R.id.tvDate)
        var btnUpdate : Button = view.findViewById(R.id.btnUpdate)
        var btnDelete : Button = view.findViewById(R.id.btnDelete)
        var todoList : TextView = view.findViewById(R.id.tvTodo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_task,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTitle.setText(list[position].taskDataClass.title) //to get data from previous database add its name like taskDataClass.title
        holder.tvDescription.setText(list[position].taskDataClass.description)
        var createdDate = Calendar.getInstance()
        createdDate.time = list[position].taskDataClass.createDate
        holder.tvDate.setText(SimpleDateFormat("dd/MMM/YYYY").format(createdDate.time))
        when(list[position].taskDataClass.priority){
            1->
                holder.itemView.setBackgroundColor(ContextCompat.getColor(context,R.color.orange))
            2->
                holder.itemView.setBackgroundColor(ContextCompat.getColor(context,R.color.blue))
            3->
                holder.itemView.setBackgroundColor(ContextCompat.getColor(context,R.color.green))
        }
        holder.btnUpdate.setOnClickListener {
            taskClickInterface.updateTask(position)
        }
        holder.btnDelete.setOnClickListener {
            taskClickInterface.deleteTask(position)
        }
        holder.itemView.setOnClickListener{
            taskClickInterface.itemClick(position)
        }
        holder.todoList.setText(
            list[position].todoList.toString()
        )
    }
}