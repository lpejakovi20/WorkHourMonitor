package hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.adapters

import android.app.Activity
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.Database
import hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.R
import hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.Task

class ToDoListFragmentAdapter(private val dataList: MutableList<Task>, private val context: Activity) :
    RecyclerView.Adapter<ToDoListFragmentAdapter.ViewHolder>() {

    private var database: Database? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.redak_liste, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = dataList[position]

        database = Database.getInstance()

        holder.textView.text = data.getText()

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView
        var btnEdit: ImageView
        var btnDelete: ImageView

        init {
            textView = itemView.findViewById(R.id.text_view)
            btnEdit = itemView.findViewById(R.id.btn_edit)
            btnDelete = itemView.findViewById(R.id.btn_delete)
        }
    }
}