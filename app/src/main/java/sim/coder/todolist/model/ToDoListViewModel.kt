package sim.coder.todolist.model

import androidx.lifecycle.ViewModel
import sim.coder.todolist.ToDoRepository

class ToDoListViewModel : ViewModel() {



    private  val toDoRepository= ToDoRepository.get()
    val taskListLiveData=toDoRepository.getTasks()


    val taskListLiveDataInProgress=toDoRepository.getTasksInProgress()
    val taskListLiveDataDone=toDoRepository.getTasksDone()



    fun updateTaskByPosition(tasks: ToDo, level:Int){
        toDoRepository.updateTaskByPosition(tasks,level)
    }




    fun addTask(tasks: ToDo){
        toDoRepository.addNewTask(tasks)
    }

    fun deleteTask(tasks: ToDo){
        toDoRepository.deleteTask(tasks)
    }



}