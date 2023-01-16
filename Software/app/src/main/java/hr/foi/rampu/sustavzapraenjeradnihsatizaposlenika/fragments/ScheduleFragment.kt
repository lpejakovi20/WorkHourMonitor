package hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.*
import hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.adapters.ActivitiesFragmentAdapter
import java.text.SimpleDateFormat
import java.util.*


class ScheduleFragment : Fragment() {

    lateinit var btnPon: Button
    lateinit var btnUto: Button
    lateinit var btnSri: Button
    lateinit var btnCet: Button
    lateinit var btnPet: Button
    lateinit var btnSub: Button
    lateinit var btnNed: Button
    lateinit var recyclerView: RecyclerView

    var calendar: Calendar = Calendar.getInstance()
    val sdf = SimpleDateFormat("yyyy-MM-dd")

    var dataList: List<Activity> = ArrayList<Activity>()

    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var activitiesFragmentAdapter: ActivitiesFragmentAdapter

    @SuppressLint("MissingInflatedId")

    fun calculateDate(pickedDay:Int){

        val currentDate = sdf.format(calendar.time)


        val dayOfWeek = calendar[Calendar.DAY_OF_WEEK]
        if(dayOfWeek - 1 > pickedDay){
            val numDays = dayOfWeek-1 - pickedDay;
            calendar.add(Calendar.DATE, numDays)
        }
        else if(dayOfWeek - 1 < pickedDay){
            val numDays = (pickedDay - (dayOfWeek-1) );
            calendar.add(Calendar.DATE, numDays)

        }
        else{
              calendar.add(Calendar.DATE, 0)
        }
    }

    fun showActivitiesByDay(pickedDay:Int,view: View){
        calendar = Calendar.getInstance()
         calculateDate(pickedDay)

        val trueMonth:String
        if(calendar.get(Calendar.MONTH)+1 < 10){
            trueMonth = "0"+(calendar.get(Calendar.MONTH)+1)
        }
        else trueMonth = (calendar.get(Calendar.MONTH)+1).toString()

        val trueDay:String
        if(calendar.get(Calendar.DAY_OF_MONTH)<10){
            trueDay = "0"+calendar.get(Calendar.DAY_OF_MONTH)
        }
        else trueDay = calendar.get(Calendar.DAY_OF_MONTH).toString()

        var loggedUser: User? = null;
        loggedUser = Database.getInstance().getUsersDAO().getUserByEmail(UserData.data.toString());

         dataList = Database.getInstance().getActivitiesDAO().getActivitiesByDate(calendar.get(Calendar.YEAR).toString(),trueMonth,trueDay,loggedUser.id)
        //dataList = Database.getInstance().getActivitiesDAO().getActivitiesByDate("2023","01","01")

        linearLayoutManager = LinearLayoutManager(view.context)
        recyclerView.layoutManager = linearLayoutManager

        activitiesFragmentAdapter =
            activity?.let {
                ActivitiesFragmentAdapter(
                    dataList as MutableList<Activity>,
                    it
                )
            }!!

        recyclerView.adapter = activitiesFragmentAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_schedule, container, false)

        recyclerView = view.findViewById(R.id.rv_list_activities)

        btnPon = view.findViewById(R.id.btnPon)
        btnUto = view.findViewById(R.id.btnUto)
        btnSri = view.findViewById(R.id.btnSri)
        btnCet = view.findViewById(R.id.btnCet)
        btnPet = view.findViewById(R.id.btnPet)
        btnSub = view.findViewById(R.id.btnSub)
        btnNed = view.findViewById(R.id.btnNed)

        Database.buildInstance(view.context)

        var mockDataLoader = MockDataLoader()
        mockDataLoader.loadMockData()




            btnPon.setOnClickListener {
                showActivitiesByDay(1,view)
            }


            btnUto.setOnClickListener {
                showActivitiesByDay(2,view)
            }
            btnSri.setOnClickListener {
                showActivitiesByDay(3,view)
            }
            btnCet.setOnClickListener {
                showActivitiesByDay(4,view)
            }
            btnPet.setOnClickListener {
                showActivitiesByDay(5,view)
            }
            btnSub.setOnClickListener {
                showActivitiesByDay(6,view)
            }
            btnNed.setOnClickListener {
                showActivitiesByDay(7,view)

        };







        return view;





    }

}