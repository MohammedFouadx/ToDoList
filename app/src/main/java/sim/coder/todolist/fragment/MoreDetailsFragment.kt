package sim.coder.todolist.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.Observer
import sim.coder.todolist.R
import sim.coder.todolist.model.MoreDetailsViewModel
import sim.coder.todolist.model.ToDo
import java.text.SimpleDateFormat
import java.util.*


class MoreDetailsFragment : Fragment(),DatePickerFragment.Callbacks {

    private lateinit var mytodo:ToDo
    private lateinit var titleEditText: EditText
    private lateinit var detailsEditText: EditText
    private lateinit var imageDateButton: ImageButton
    private val simpleDateFormat = SimpleDateFormat("EEE,MMM,yyyy,dd")
    private val date= simpleDateFormat.format(Date())

    private val moreDetailsViewModel:MoreDetailsViewModel by lazy {
        ViewModelProviders.of(this).get(MoreDetailsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

         mytodo = ToDo()
        val id:Int = arguments?.getSerializable("ARG_TASK_ID") as Int
        moreDetailsViewModel.loadTask(id)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_more_details, container, false)
        titleEditText=view.findViewById(R.id.editTextTextPersonName) as EditText
        detailsEditText=view.findViewById(R.id.editTextTextPersonName2) as EditText
        imageDateButton=view.findViewById(R.id.imageButton) as ImageButton


        imageDateButton.setOnClickListener {
            DatePickerFragment.newInctance(mytodo.date).apply {
                setTargetFragment(this@MoreDetailsFragment,0)
                show(this@MoreDetailsFragment.requireFragmentManager(), "input")

            }
        }



        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moreDetailsViewModel.taskLiveData.observe(
                viewLifecycleOwner,
                Observer {todo ->
                    todo?.let {todo->
                        this.mytodo = todo
                        updateUI()
                    }
                }
        )
    }

    override fun onStop() {
        super.onStop()
        moreDetailsViewModel.saveTask(mytodo)

    }

    fun updateUI() {
        titleEditText.setText(mytodo.title)
        detailsEditText.setText(mytodo.details)

    }


    override fun onStart() {
        super.onStart()

        val titleWatcher = object : TextWatcher {

            override fun beforeTextChanged(
                    sequence: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
            ) {
                // This space intentionally left blank
            }

            override fun onTextChanged(
                    sequence: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
            ) {
                mytodo.title = sequence.toString()
                mytodo.details = sequence.toString()
            }

            override fun afterTextChanged(sequence: Editable?) {
                // This one too
            }
        }

        titleEditText.addTextChangedListener(titleWatcher)
        detailsEditText.addTextChangedListener(titleWatcher)



    }

    companion object {
        fun newInstance(taskId:Int) : MoreDetailsFragment{

            val args=Bundle().apply {
                putSerializable("ARG_TASK_ID",taskId)
            }
            return MoreDetailsFragment().apply {
                arguments=args
            }
        }
    }

    override fun onDateSelected(date: Date) {
        mytodo.date
    }



}