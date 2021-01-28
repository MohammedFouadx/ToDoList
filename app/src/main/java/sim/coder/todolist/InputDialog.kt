package sim.coder.todolist

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.DialogFragment
import sim.coder.todolist.fragment.DatePickerFragment
import sim.coder.todolist.model.ToDo
import java.text.SimpleDateFormat
import java.util.*


class InputDialog:DialogFragment(), DatePickerFragment.Callbacks {


    private  var  date = SimpleDateFormat("yyy MM dd")

    private lateinit var toDo: ToDo

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        toDo= ToDo(0,"","", Date())
        val view = activity?.layoutInflater?.inflate(R.layout.custom_dialog_layout, null)

        val title = view?.findViewById(R.id.et_title_task) as EditText
        val details = view?.findViewById(R.id.et_details_task) as EditText
        val imageButtonDate=view?.findViewById(R.id.image_pick_date)as ImageButton


        imageButtonDate.setOnClickListener {
            DatePickerFragment.newInctance(toDo.date).apply {
                setTargetFragment(this@InputDialog,0)
                show(this@InputDialog.requireFragmentManager(), "input")

            }
        }

        return  AlertDialog.Builder(requireContext(),R.style.ThemeOverlay_AppCompat_Dialog_Alert)
            .setView(view)
            .setCancelable(false)
            .setPositiveButton("Save"){dialog,_->
                val student=ToDo(0,title.text.toString(),
                   details.text.toString(),toDo.date)
                targetFragment.let {fragment ->
                    (fragment as Callbacks).addTask(student)
                }
            }.setNegativeButton("cancel"){dialog,_->
                dialog.cancel()
            }.create()
    }

    interface Callbacks{

        fun addTask(todo : ToDo)
    }

    override fun onDateSelected(date: Date) {
        toDo.date = date


    }
}