package com.vanshika.recyclerviewapplication

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@TypeConverters(DateTimeConverter::class)
@Database(entities = [TaskDataClass::class, TodoEntity::class],version = 1, exportSchema = true)
abstract class TodoDatabase: RoomDatabase() {
    abstract fun todoDao() : TodoDao
    companion object{
        private var todoDatabase : TodoDatabase ?= null
        fun getInstance(context: Context): TodoDatabase{
            if (todoDatabase == null){
                todoDatabase = Room.databaseBuilder(context,
                TodoDatabase::class.java,
                "TodoDatabase")
                    .allowMainThreadQueries()
                    .build()
            }
            return todoDatabase !!
        }
    }
}