package hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.adapters

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.baza.Database
import hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.R
import hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.baza.Entities.Task
import hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.UserData

class ToDoListFragmentAdapter(private val dataList: MutableList<Task>, private val context: Activity) :
    RecyclerView.Adapter<ToDoListFragmentAdapter.ViewHolder>() {

    private var database: Database? = null
    var builder: AlertDialog.Builder? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.redak_liste, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = dataList[position]

        database = Database.getInstance()

        holder.textView.text = data.getText()

        holder.btnDelete.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                builder = AlertDialog.Builder(v.context)
                builder!!.setMessage("Jeste li sigurni da želite obrisati odabrani zadatak?")
                    .setCancelable(false)
                    .setPositiveButton("Da", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface, id: Int) {

                            val d = dataList[holder.adapterPosition]

                            Database.getInstance().getTasksDAO().removeTask(d)

                            val position = holder.adapterPosition
                            dataList.removeAt(position)
                            notifyItemRemoved(position)
                            notifyItemRangeChanged(position, dataList.size)
                        }
                    })
                    .setNegativeButton("Ne", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface, id: Int) {
                            dialog.cancel()
                        }
                    })
                val alert = builder!!.create()
                alert.setTitle("Potvrda brisanja")
                alert.show()
            }
        })

        holder.btnEdit.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val d = dataList[holder.adapterPosition]

                val sID = d.getID()
                val sText = d.getText()

                val dialog = Dialog(context)

                dialog.setContentView(R.layout.azuriranje_zadatka)

                val width = WindowManager.LayoutParams.MATCH_PARENT
                val height = WindowManager.LayoutParams.WRAP_CONTENT
                dialog.window!!.setLayout(width, height)

                dialog.show()

                val editText = dialog.findViewById<EditText>(R.id.edit_text)
                val btUpdate = dialog.findViewById<Button>(R.id.bt_update)

                editText.setText(sText)
                btUpdate.setOnClickListener(View.OnClickListener {
                    dialog.dismiss()
                    val uText = editText.text.toString().trim { it <= ' ' }

                    Database.getInstance().getTasksDAO().update(sID, uText)
                    dataList.clear()
                    val loggedUser = Database.getInstance().getUsersDAO()
                        .getUserByEmail(UserData.data.toString())

                    dataList.addAll(Database.getInstance().getTasksDAO().getAllTasksOfUser(loggedUser.id))
                    notifyDataSetChanged()
                })
            }
        })
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