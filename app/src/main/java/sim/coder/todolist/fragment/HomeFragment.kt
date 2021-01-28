package sim.coder.todolist.fragment

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import sim.coder.todolist.InputDialog
import sim.coder.todolist.R
import sim.coder.todolist.model.ToDo
import sim.coder.todolist.model.ToDoListViewModel


class HomeFragment : Fragment(),InputDialog.Callbacks {


    interface CallBacks{
        fun onTaskSelected(taskId: Int)
    }

    private var callbacks: CallBacks? = null
    private lateinit var addNote:FloatingActionButton
    private lateinit var toDORecyclerView: RecyclerView
    private  var fragmentName : String? = null
    private var adapter: TodoAdapter? = TodoAdapter(emptyList())
    private lateinit var todoModel: ToDo







    private val todoListViewModel by lazy {
        ViewModelProviders.of(this).get(ToDoListViewModel::class.java)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks=context as CallBacks?
    }

    override fun onDetach() {
        super.onDetach()
        callbacks=null
    }






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        todoModel= ToDo()



    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        toDORecyclerView = view.findViewById(R.id.todo_recycler_view) as RecyclerView
        toDORecyclerView.layoutManager = LinearLayoutManager(context)
        fragmentName=arguments?.getSerializable("data")as String
        addNote=view.findViewById(R.id.floating_add_task)
        toDORecyclerView.adapter = adapter





        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        when(fragmentName){
            "todo" ->{
                addNote.visibility=View.VISIBLE
                todoListViewModel.taskListLiveData .observe(
                        viewLifecycleOwner,
                        Observer { tasks ->
                            tasks?.let {
                                updateUI(tasks)

                            }
                        })
            }

            "inprogress" ->{
                addNote.visibility=View.GONE
                todoListViewModel.taskListLiveDataInProgress .observe(
                        viewLifecycleOwner,
                        Observer { tasks ->
                            tasks?.let {
                                updateUI(tasks)

                            }
                        })
            }

            "done" ->{
                addNote.visibility=View.GONE
                todoListViewModel.taskListLiveDataDone .observe(
                        viewLifecycleOwner,
                        Observer { tasks ->
                            tasks?.let {
                                updateUI(tasks)


                            }
                        })
            }
        }

//        todoListViewModel.taskListLiveData.observe(
//                viewLifecycleOwner,
//                Observer { toDO->
//                    toDO.let {
//                        updateUI(toDO)
//                    }
//
//                }
//        )

        addNote.setOnClickListener {
            InputDialog().apply {
                setTargetFragment(this@HomeFragment,0)
                show(this@HomeFragment.requireFragmentManager(),"input")
            }
            true
        }






    }

    private inner class TodoHolder(view: View):RecyclerView.ViewHolder(view),View.OnClickListener{
        private lateinit var todoModel: ToDo
        val todoTitle: TextView =itemView.findViewById(R.id.title_task)
        val todoDate: TextView =itemView.findViewById(R.id.date_task)
        val startTask: ImageButton = itemView.findViewById(R.id.start_task_btn)
        val doneTask: ImageButton = itemView.findViewById(R.id.done_task_btn)
        val deleteTask: ImageButton = itemView.findViewById(R.id.delete_task_btn)
        val cardView:CardView = itemView.findViewById(R.id.cardView)
        val preTask:ImageButton = itemView.findViewById(R.id.pre_task_btn)
        //val todoDetails :TextView=itemView.findViewById(R.id.detail_task)
        //val delete :ImageButton=itemView.findViewById(R.id.imageButton2)





        init {

            cardView.setOnClickListener(this)

            deleteTask.setOnClickListener {
                val builder= AlertDialog.Builder(requireContext())
                builder.setPositiveButton("Yes"){ _,_ ->
                    todoListViewModel.deleteTask(todoModel)
                    Toast.makeText(requireContext(),"task has been deleted successfully",Toast.LENGTH_LONG).show()

                }
                builder.setNegativeButton("No"){ _,_ -> }
                builder.setTitle("Delete")
                builder.setTitle("Are you sure you want to delete")
                builder.create().show()
            }

            if (fragmentName == "todo"){
                preTask.visibility=View.GONE
                doneTask.visibility=View.GONE
            }else if(fragmentName == "inprogress"){
                startTask.visibility=View.GONE
            }else{
                startTask.visibility=View.GONE
                doneTask.visibility=View.GONE
            }
            startTask.setOnClickListener {
                startTask.visibility=View.GONE
               todoListViewModel.updateTaskByPosition(todoModel,1)

            }

            doneTask.setOnClickListener {
                startTask.visibility=View.GONE
                doneTask.visibility=View.GONE
              todoListViewModel.updateTaskByPosition(todoModel,2)

            }
            preTask.setOnClickListener {
                if (fragmentName == "inprogress") {
                    todoListViewModel.updateTaskByPosition(todoModel, 0)
                }else if(fragmentName == "done"){
                    todoListViewModel.updateTaskByPosition(todoModel,1)

                }
            }
        }

        fun bind(item: ToDo) {

            this.todoModel = item
            todoTitle.text = this.todoModel.title
            //todoDetails.text= this.todoModel.details
            todoDate.text = this.todoModel.date.toString()

//            delete.setOnClickListener {
//                todoListViewModel.deleteTask(todoModel)
//            }


        }




        override fun onClick(v: View?) {
            callbacks?.onTaskSelected(todoModel.id)
        }


    }




    private inner class TodoAdapter(var todo:List<ToDo>) :RecyclerView.Adapter<TodoHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoHolder {
            val view = layoutInflater.inflate(R.layout.list_item_to_do, parent, false)
            return TodoHolder(view)
        }

        override fun getItemCount(): Int {
            return todo.size
        }

        override fun onBindViewHolder(holder: TodoHolder, position: Int) {
            val todo = todo[position]
            holder.bind(todo)
        }

    }



    private fun updateUI(todo: List<ToDo>) {

        adapter=TodoAdapter(todo)
        toDORecyclerView.adapter=adapter

    }




    companion object{
        fun newInstance(data:String):HomeFragment{
            val args=Bundle().apply {
                putSerializable("data",data)
            }
            return  HomeFragment().apply {
                arguments=args
            }
        }
    }

    override fun addTask(todo: ToDo) {
        todoListViewModel.addTask(todo)

    }




}


