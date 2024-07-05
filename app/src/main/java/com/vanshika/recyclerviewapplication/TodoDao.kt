package com.vanshika.recyclerviewapplication

import android.devicelock.DeviceId
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TodoDao {
    @Insert
    fun insertTodo(taskDataClass: TaskDataClass)

    @Query("SELECT * FROM TaskDataClass")
    fun getList() : List<TaskDataClass>

    @Update
    fun updateList(taskDataClass: TaskDataClass)

    @Delete
    fun deleteList(taskDataClass: TaskDataClass)

    @Query("SELECT * FROM TaskDataClass WHERE priority =:priority")
    fun changeAccPriority(priority : Int) : List<TaskDataClass>
}