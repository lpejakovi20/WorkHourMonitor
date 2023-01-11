package hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
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

        return view
    }

}