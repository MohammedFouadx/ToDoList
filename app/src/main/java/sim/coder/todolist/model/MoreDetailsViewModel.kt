package sim.coder.todolist.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import sim.coder.todolist.ToDoRepository

class MoreDetailsViewModel : ViewModel() {

    private val todoRepositore = ToDoRepository.get()
    private val taskIdLiveDate = MutableLiveData<Int>()

    var taskLiveData:LiveData<ToDo?> = Transformations.switchMap(taskIdLiveDate) {taskId ->
        todoRepositore.getTask(taskId)

    }

    fun loadTask(taskId:Int){
        taskIdLiveDate.value = taskId
    }

    fun saveTask(task: ToDo) {
        todoRepositore.updatetask(task)
    }

}