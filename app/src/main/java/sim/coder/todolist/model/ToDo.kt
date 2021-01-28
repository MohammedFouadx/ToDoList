package sim.coder.todolist.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity
class ToDo(
    @PrimaryKey (autoGenerate = true) var id:Int=0,
    var title:String="",
    var details:String="",
    var date : Date= Date(),
    var task : Int =0
)  {
}