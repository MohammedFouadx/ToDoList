package sim.coder.todolist.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import sim.coder.todolist.model.ToDo

@Database (entities = [ToDo::class],version = 1 , exportSchema = false)
@TypeConverters(ToDoTypeConverters::class)
abstract class ToDoDatabase:RoomDatabase() {

    abstract fun todoDao():Dao

}


