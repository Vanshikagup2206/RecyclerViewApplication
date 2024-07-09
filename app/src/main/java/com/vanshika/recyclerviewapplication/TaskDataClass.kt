package com.vanshika.recyclerviewapplication

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar
import java.util.Date

@Entity
data class TaskDataClass(
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    var title :String ?="",
    var description : String ?="",
    var priority : Int ?= 0,
    var createDate : Date?= Calendar.getInstance().time
)
