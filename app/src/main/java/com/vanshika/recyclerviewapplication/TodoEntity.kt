package com.vanshika.recyclerviewapplication

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import java.util.Calendar
import java.util.Date

@Entity(foreignKeys = [
    ForeignKey(entity = TaskDataClass::class,
    parentColumns = ["id"],
    childColumns = ["taskId"],
    onDelete = CASCADE)
])

data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    var taskId : Int = 0,
    var todo : String = "",
    var isCompleted : Boolean ?= false,
    var createdDate : Date?= Calendar.getInstance().time
)
{
    override fun toString(): String {
        return "$todo"
    }
}
