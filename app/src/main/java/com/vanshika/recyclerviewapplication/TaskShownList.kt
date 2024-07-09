package com.vanshika.recyclerviewapplication

import androidx.room.Embedded
import androidx.room.Relation

data class TaskShownList(  //you have to make different class for foreign key
    @Embedded
    var taskDataClass: TaskDataClass,

    @Relation(entity = TodoEntity::class,
    parentColumn = "id",
    entityColumn = "taskId")
    var todoList: List<TodoEntity>
)
