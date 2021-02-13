package sim.coder.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProviders
import sim.coder.todolist.model.ToDo
import sim.coder.todolist.model.ToDoListViewModel
import java.text.SimpleDateFormat
import java.util.*

class MoreDetailsActivity : AppCompatActivity()    {

    private lateinit var mytodo: ToDo
    private lateinit var titleEditText: EditText
    private lateinit var detailsEditText: EditText
    private lateinit var dateTextView: TextView
    private lateinit var titleInProgressDone :TextView
    private lateinit var detailsInProgressDone :TextView
    private val simpleDateFormat = SimpleDateFormat("MMM dd , yyyy , HH:mm" )


    private val toDoListViewModel: ToDoListViewModel by lazy {
        ViewModelProviders.of(this).get(ToDoListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_details)
        mytodo = ToDo(0,"","", Date() , 0)

        setSupportActionBar(findViewById(R.id.more_details_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.customView
        supportActionBar?.setDisplayShowTitleEnabled(false)


        titleEditText=findViewById(R.id.editTextTextPersonName)
        detailsEditText=findViewById(R.id.editTextTextPersonName2)
        dateTextView=findViewById(R.id.editTextTextPersonName3)
        titleInProgressDone=findViewById(R.id.title_inProgress)
        detailsInProgressDone=findViewById(R.id.details_inProgress)


        var intent :ToDo= intent.getSerializableExtra("key") as ToDo
        titleEditText.setText(intent.title)
        detailsEditText.setText(intent.details)
        dateTextView.setText(simpleDateFormat.format(intent.date))


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_more_details, menu)
        return super.onCreateOptionsMenu(menu)

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId){
            R.id.save ->
            {
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
                Toast.makeText(this,"Task has been updated successfully",Toast.LENGTH_LONG).show()
                true

            }

            else -> super.onOptionsItemSelected(item)

        }



    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
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
            }

            override fun afterTextChanged(sequence: Editable?) {
               toDoListViewModel.updateTask(mytodo)

            }
        }

        titleEditText.addTextChangedListener(titleWatcher)

        }


    override fun onStop() {
        super.onStop()

        toDoListViewModel.updateTask(mytodo)
    }

    fun updateUI(){
        titleEditText.setText(mytodo.title)
        detailsEditText.setText(mytodo.details)
        //dateTextView.text=simpleDateFormat.format(mytodo.date)
    }




}


