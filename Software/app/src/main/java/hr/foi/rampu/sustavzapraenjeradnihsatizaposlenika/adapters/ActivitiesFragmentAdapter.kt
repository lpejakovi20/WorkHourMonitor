package hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.adapters

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.baza.Database
import hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.R
import hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.baza.Entities.Activity


class ActivitiesFragmentAdapter(private val dataList: MutableList<Activity>, private val context: android.app.Activity) :
    RecyclerView.Adapter<ActivitiesFragmentAdapter.ViewHolder>() {

    private var database: Database? = null
    var builder: AlertDialog.Builder? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActivitiesFragmentAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.redak_aktivnosti, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ActivitiesFragmentAdapter.ViewHolder, position: Int) {

        val data = dataList[position]
        database = Database.getInstance()
        holder.textView.text = data.getText()

        val dateTime =data.start_of_activity
        val time = dateTime.split(" ")

        val dateTimeEnd =data.end_of_activity
        val timeEnd = dateTimeEnd.split(" ")

        holder.starTime.text = time[1] + " - " +timeEnd[1]
    }
    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView
        var starTime : TextView

        init {
            textView = itemView.findViewById(R.id.tv_activity_name)
            starTime = itemView.findViewById(R.id.tv_start_time)
            }
        }
    }
