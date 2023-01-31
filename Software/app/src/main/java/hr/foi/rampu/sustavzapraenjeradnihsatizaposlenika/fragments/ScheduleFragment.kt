package hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
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
    lateinit var txtDate : TextView
    lateinit var txtWeek : TextView
    lateinit var previousButton: Button

    var calendar: Calendar = Calendar.getInstance()
    val sdf = SimpleDateFormat("yyyy-MM-dd")

    var dataList: List<Activity> = ArrayList<Activity>()

    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var activitiesFragmentAdapter: ActivitiesFragmentAdapter

    @SuppressLint("MissingInflatedId")

    fun calculateDate(pickedDay:Int){

        val dayOfWeek = calendar[Calendar.DAY_OF_WEEK]
        if(dayOfWeek - 1 > pickedDay){
            val numDays = dayOfWeek-1 - pickedDay;
            calendar.add(Calendar.DATE, numDays*-1)
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

        txtDate = view.findViewById(R.id.txtDate)
        txtDate.text = trueDay +"."+trueMonth+"."+calendar.get(Calendar.YEAR).toString()
        var loggedUser: User? = null;
        loggedUser = Database.getInstance().getUsersDAO().getUserByEmail(UserData.data.toString());
         dataList = Database.getInstance().getActivitiesDAO().getActivitiesByDate(calendar.get(Calendar.YEAR).toString(),trueMonth,trueDay,loggedUser.id)


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

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_schedule, container, false)

        recyclerView = view.findViewById(R.id.rv_list_activities)

        txtWeek = view.findViewById(R.id.txtWeek)
        var calendarStart = Calendar.getInstance()
        var calendarEnd = Calendar.getInstance()

        val dayStart = calendarStart[Calendar.DAY_OF_WEEK]
        if(dayStart - 1 > 1){
            val numDays = dayStart-1 - 1;
            calendarStart.add(Calendar.DATE, numDays*-1)
        }
        else{
            calendarStart.add(Calendar.DATE, 0)
        }

        val dayEnd = calendarEnd[Calendar.DAY_OF_WEEK]

        if(dayStart - 1 < 7){
            val numDays = (7 - (dayEnd-1) );
            calendarEnd.add(Calendar.DATE, numDays)

        }
        else{
            calendarEnd.add(Calendar.DATE, 0)
        }

        val date = calendarStart.get(Calendar.DAY_OF_MONTH).toString() + ". " + (calendarStart.get(Calendar.MONTH)+1).toString()+" - "+
                calendarEnd.get(Calendar.DAY_OF_MONTH).toString()+ ". " + (calendarEnd.get(Calendar.MONTH)+1).toString()+". "+calendarStart.get(Calendar.YEAR).toString()

        txtWeek.text = date

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

        val dayOfWeek = calendar[Calendar.DAY_OF_WEEK]
        showActivitiesByDay(dayOfWeek-1,view)

        val originalColor = btnPon.backgroundTintList

        previousButton = btnPon

        btnPon.setOnClickListener {
            previousButton.backgroundTintList = originalColor
            btnPon.backgroundTintList = ContextCompat.getColorStateList(view.context, R.color.hyperlink)
                showActivitiesByDay(1,view)
            previousButton = btnPon
            }
            btnUto.setOnClickListener {
                previousButton.backgroundTintList = originalColor
                btnUto.backgroundTintList = ContextCompat.getColorStateList(view.context, R.color.hyperlink)
                showActivitiesByDay(2,view)
                previousButton = btnUto
            }
            btnSri.setOnClickListener {
                previousButton.backgroundTintList = originalColor
                btnSri.backgroundTintList = ContextCompat.getColorStateList(view.context, R.color.hyperlink)
                showActivitiesByDay(3,view)
                previousButton = btnSri
            }
            btnCet.setOnClickListener {
                previousButton.backgroundTintList = originalColor
                btnCet.backgroundTintList = ContextCompat.getColorStateList(view.context, R.color.hyperlink)
                showActivitiesByDay(4,view)
                previousButton = btnCet
            }
            btnPet.setOnClickListener {
                previousButton.backgroundTintList = originalColor
                btnPet.backgroundTintList = ContextCompat.getColorStateList(view.context, R.color.hyperlink)
                showActivitiesByDay(5,view)
                previousButton = btnPet
            }
            btnSub.setOnClickListener {
                previousButton.backgroundTintList = originalColor
                btnSub.backgroundTintList = ContextCompat.getColorStateList(view.context, R.color.hyperlink)
                showActivitiesByDay(6,view)
                previousButton = btnSub
            }
            btnNed.setOnClickListener {
                previousButton.backgroundTintList = originalColor
                btnNed.backgroundTintList = ContextCompat.getColorStateList(view.context, R.color.hyperlink)
                showActivitiesByDay(7,view)
                previousButton = btnNed

        };


        return view;

    }

}