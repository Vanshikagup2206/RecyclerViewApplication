package com.vanshika.recyclerviewapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoItemRecycler(var todoEntity: ArrayList<TodoEntity>, var singleNotesInterface : SingleNotesInterface) : RecyclerView.Adapter<TodoItemRecycler.ViewHolder>(){
    class ViewHolder(var view : View): RecyclerView.ViewHolder(view){
        var tvTodo : TextView = view.findViewById(R.id.tvTodo)
        var cbCompleted : CheckBox = view.findViewById(R.id.cbCompleted)
        var btnUpdate : Button = view.findViewById(R.id.btnUpdate)
        var btnDelete : Button = view.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.todo_items,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return todoEntity.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTodo.setText(todoEntity[position].todo)
        if (todoEntity[position].isCompleted == true){
            holder.cbCompleted.isChecked
        }
        holder.btnUpdate.setOnClickListener {
            singleNotesInterface.updateSingleNotes(position)
        }
        holder.btnDelete.setOnClickListener {
            singleNotesInterface.deleteSingleNotes(position)
        }
    }
}