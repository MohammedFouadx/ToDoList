package sim.coder.todolist.db

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import sim.coder.todolist.model.ToDo
import java.util.*


@Dao
interface Dao {

    @Insert
    fun addTask(task:ToDo)

    @Query("SELECT * FROM ToDo where task=:task ORDER BY id DESC")
    fun getTasks(task: Int):LiveData<List<ToDo>>

    @Query("SELECT * FROM ToDo WHERE id=(:id)")
    fun getTask(id: Int): LiveData<ToDo?>

    //update
    @Query("UPDATE ToDo SET task=:task where id= :id ")
    fun updateTaskByposition(task: Int, id:Int )


    @Update
    fun updateTask(todo: ToDo)

    @Delete
    fun deleteTask(task:ToDo)


}

