package sim.coder.todolist.model

import android.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity
data class ToDo(
        @PrimaryKey (autoGenerate = true) var id:Int=0,
        var title:String="",
        var details:String="",
        var date: Date= Date(),
        var color : Int = 0,
        var task: Int = 0




) :Serializable {

}



