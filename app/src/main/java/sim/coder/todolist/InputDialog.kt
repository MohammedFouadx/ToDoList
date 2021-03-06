package sim.coder.todolist

import android.app.*
import android.graphics.Color
import android.os.Bundle
import android.widget.*
import androidx.fragment.app.DialogFragment
import petrov.kristiyan.colorpicker.ColorPicker
import sim.coder.todolist.fragment.DatePickerFragment
import sim.coder.todolist.fragment.TimePickerFragment
import sim.coder.todolist.model.ToDo
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

private const val DIALOG_TIME = "DialogTime"
private const val REQUEST_TIME = 0
class InputDialog : DialogFragment() , DatePickerFragment.Callbacks , TimePickerFragment.Callbacks{

    private lateinit var toDo: ToDo
    private val simpleDateFormat = SimpleDateFormat("MMM d, yyyy")
    private val simpleTimeFormat = SimpleDateFormat("HH:mm")
    private lateinit var setAlarm :TextView
    private lateinit var setDate :TextView
    private lateinit var pickColor:ImageButton
    private lateinit var colorTextView: TextView
    private lateinit var title:EditText
    private lateinit var details:EditText
    private lateinit var imageButtonDate:ImageButton
    private lateinit var imageButtonTime:ImageButton


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        toDo= ToDo(0,"","", Date() , 0)
        val view = activity?.layoutInflater?.inflate(R.layout.custom_dialog_layout, null)

         title = view?.findViewById(R.id.et_title_task) as EditText
         details = view?.findViewById(R.id.et_details_task) as EditText
//        setAlarm = view?.findViewById(R.id.textView2) as TextView
//        setDate = view?.findViewById(R.id.textView) as TextView
        imageButtonDate=view?.findViewById(R.id.image_pick_date)as ImageButton
        imageButtonTime = view?.findViewById(R.id.image_pick_time) as ImageButton
        pickColor=view?.findViewById(R.id.image_pick_color) as ImageButton
        //colorTextView=view.findViewById(R.id.text_color) as TextView


//        fun openColorPicker(){
//            val pick = ColorPicker(activity)
//            val colors = ArrayList<String>()
//            colors.add("#FFBB86FC")
//            colors.add("#4B0082")
//            colors.add("#32CD32")
//            colors.add("#FF018786")
//
//            pick.setColors(colors)
//                .setColumns(5)
//                .setRoundColorButton(true)


       // }

        pickColor.setOnClickListener {
            val pick = ColorPicker(activity)
                .setRoundColorButton(true)

        pick.setOnChooseColorListener(object : ColorPicker.OnChooseColorListener{
            override fun onChooseColor(position: Int, color: Int) {

                toDo.color = color
            }

            override fun onCancel() {

            }

        }).show()


        }



        imageButtonTime.setOnClickListener {
            TimePickerFragment.newInstance(toDo.date).apply {
                setTargetFragment(this@InputDialog, REQUEST_TIME)
                show(this@InputDialog.requireFragmentManager(), DIALOG_TIME)

            }

        }

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
                if (title.text.toString().isNotEmpty() && details.text.toString().isNotEmpty() ){
                    val student=ToDo(0,title.text.toString(),
                            details.text.toString(),toDo.date  , toDo.color )
                    targetFragment.let {fragment ->
                        (fragment as Callbacks).addTask(student)
                        dialog.cancel()
                    }

                } else{
                    Toast.makeText(context,"Please  Enter title and details of task",Toast.LENGTH_LONG).show()
                }


            }.setNegativeButton("cancel"){dialog,_->
            }.create()
    }

    interface Callbacks{

        fun addTask(todo : ToDo)

    }

    override fun onDateSelected(date: Date) {
        toDo.date = date
        //setDate.setText(simpleDateFormat.format(toDo.date))


    }



    override fun onTimeSelected(time: Date) {
        toDo.date = time
        //setAlarm.setText(simpleTimeFormat.format(toDo.date))
    }



}