package sim.coder.todolist

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import sim.coder.todolist.db.ToDoDatabase
import sim.coder.todolist.model.ToDo
import java.lang.IllegalStateException
import java.util.concurrent.Executors

private const val DATABASE_NAME= "todo-database"
class ToDoRepository private constructor(context: Context){


    private val database :ToDoDatabase = Room.databaseBuilder(
            context.applicationContext,
            ToDoDatabase::class.java,
            DATABASE_NAME

    ).build()

    private val ToDoDao = database.todoDao()


    fun getTasks(): LiveData<List<ToDo>> = ToDoDao.getTasks(0)
    fun getTasksInProgress(): LiveData<List<ToDo>> = ToDoDao.getTasks(1)
    fun getTasksDone(): LiveData<List<ToDo>> = ToDoDao.getTasks(2)
    fun getTask(id:Int): LiveData<ToDo?> = ToDoDao.getTask(id)
    private  var executor = Executors.newSingleThreadExecutor()


    fun updateTaskByPosition(tasks: ToDo, level:Int) {
        executor.execute {
            ToDoDao.updateTaskByposition(level,tasks.id)
        }
    }


    fun addNewTask(tasks: ToDo){
        executor.execute{
            ToDoDao.addTask(tasks)
        }
    }

    fun updateTask(tasks: ToDo){
        executor.execute{
            ToDoDao.updateTask(tasks)
        }
    }

    fun deleteTask(tasks: ToDo){
        executor.execute{
            ToDoDao.deleteTask(tasks)
        }

    }



    companion object {
        private var INSTANCE: ToDoRepository? = null
        fun initialize(context: Context)
        {
            if (INSTANCE == null)
            {      INSTANCE = ToDoRepository(context)
            }
        }
        fun get():
                ToDoRepository {
            return INSTANCE ?:
            throw IllegalStateException("CrimeRepository must be initialized")
        }
    }
}