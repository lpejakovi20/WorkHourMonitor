package hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.*
import hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.adapters.ToDoListFragmentAdapter


class ToDoListFragment : Fragment() {

    lateinit var editText: EditText
    lateinit var btnAdd: Button
    lateinit var btnReset: Button
    lateinit var recyclerView: RecyclerView

    var dataList: List<Task> = ArrayList<Task>()

    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var toDoListFragmentAdapter: ToDoListFragmentAdapter
    lateinit var builder: AlertDialog.Builder


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_checklist, container, false)

        editText = view.findViewById(R.id.edit_text);
        btnAdd = view.findViewById(R.id.bt_add)
        btnReset = view.findViewById(R.id.bt_reset)
        recyclerView = view.findViewById(R.id.recycler_view)

        Database.buildInstance(view.context)

        var mockDataLoader = MockDataLoader()
        mockDataLoader.loadMockData()

        var loggedUser: User? = null;
        loggedUser = Database.getInstance().getUsersDAO().getUserByEmail(UserData.data.toString());

        if (loggedUser != null) {
            dataList = Database.getInstance().getTasksDAO().getAllTasksOfUser(loggedUser.id)
        };

        linearLayoutManager = LinearLayoutManager(view.context)
        recyclerView.layoutManager = linearLayoutManager


        toDoListFragmentAdapter =
            activity?.let {
                ToDoListFragmentAdapter(
                    dataList as MutableList<Task>,
                    it
                )
            }!!

        recyclerView.adapter = toDoListFragmentAdapter

        btnAdd.setOnClickListener {
            val sText = editText.text.toString().trim { it <= ' ' }
            if (sText != "") {

                var loggedUser = Database.getInstance().getUsersDAO().getUserByEmail(UserData.data.toString())
                val data = Task(0,sText,loggedUser.id,false)
                Database.getInstance().getTasksDAO().insertTask(data)

                editText.setText("")

                (dataList as MutableList<Task>?)?.clear()
                Toast.makeText(activity, "Zadatak je uspješno dodan u listu!", Toast.LENGTH_LONG).show()

                (dataList as MutableList<Task>?)?.addAll(Database.getInstance().getTasksDAO().getAllTasksOfUser(loggedUser.id))
                toDoListFragmentAdapter.notifyDataSetChanged()
            } else {
                builder = AlertDialog.Builder(view.context)
                builder.setMessage("Polje za unos naziva zadatka ne smije biti prazno!!")
                    .setCancelable(false)
                    .setPositiveButton(
                        "Ok"
                    ) { dialog, id -> dialog.cancel() }

                val alert = builder.create()
                alert.setTitle("Nevaljan unos")
                alert.show()
            }
        }

        btnReset.setOnClickListener { v ->
            builder = AlertDialog.Builder(v.context)
            builder.setMessage("Jeste li sigurni da želite obrisati sve vaše zadatke?")
                .setCancelable(false)
                .setPositiveButton("Da") { dialog, id ->
                    val loggedUser = Database.getInstance().getUsersDAO().getUserByEmail(UserData.data.toString())

                    Database.getInstance().getTasksDAO().deleteTasksOfUser(loggedUser.id)
                    (dataList as MutableList<Task>?)?.clear()
                    (dataList as MutableList<Task>?)?.addAll(Database.getInstance().getTasksDAO().getAllTasksOfUser(loggedUser.id))
                    toDoListFragmentAdapter.notifyDataSetChanged()
                }
                .setNegativeButton("Ne") { dialog, id ->
                    dialog.cancel()
                }
            val alert = builder.create()
            alert.setTitle("Potvrda brisanja")
            alert.show()
        }

        return view
    }

}