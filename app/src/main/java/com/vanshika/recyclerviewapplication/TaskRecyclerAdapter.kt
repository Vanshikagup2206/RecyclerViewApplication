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

class TaskRecyclerAdapter(var context : Context, var list : ArrayList<TaskDataClass>, var taskClickInterface: TaskClickInterface) : RecyclerView.Adapter<TaskRecyclerAdapter.ViewHolder>() {
    class ViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        var tvTitle :TextView = view.findViewById(R.id.tvTitle)
        var tvDescription : TextView = view.findViewById(R.id.tvDescription)
        var btnUpdate : Button = view.findViewById(R.id.btnUpdate)
        var btnDelete : Button = view.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_task,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTitle.setText(list[position].title)
        holder.tvDescription.setText(list[position].description)
        when(list[position].priority){
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
    }
}