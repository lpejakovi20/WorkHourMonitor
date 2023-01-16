package hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.fragments

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.*
import java.time.LocalDateTime
import java.util.Calendar

class StatisticsFragment : Fragment() {

    lateinit var spinner: Spinner

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_statistics, container, false)

        spinner = view.findViewById<Spinner>(R.id.months_spinner)

        var myList: MutableList<String> = mutableListOf<String>("siječanj","veljača","ožujak","travanj","svibanj","lipanj","srpanj","kolovoz","rujan","listopad","studeni","prosinac")

        val current = LocalDateTime.now()
        var currentMonth = current.monthValue + 4  //+4 stavljen samo radi testiranja
        var currentYear = current.year

        myList = myList.take(currentMonth) as MutableList<String>


        val adapter = ArrayAdapter<String>(view.context,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,myList)

        spinner.adapter = adapter

        var tvHoursDone = view.findViewById<TextView>(R.id.tv_hours_done)
        var tvOvertimeHours = view.findViewById<TextView>(R.id.tv_overtime_hours)
        var tvHoursSundayOrNightShift = view.findViewById<TextView>(R.id.tv_sunday_or_night_shift)

        Database.buildInstance(view.context)
        var mockDataLoader = MockDataLoader()
        mockDataLoader.loadMockData()

        var loggedUser: User? = null;
        loggedUser = Database.getInstance().getUsersDAO().getUserByEmail(UserData.data.toString());

        var currentMonthProperFormat: String

        //potencijalno dodati da se jednom dohvate svi podaci za statistiku trenutnog korisnika u trenutnoj godini,
        // a ne da se svaki puta dohvaća

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var month = position + 1
                if(month < 10) currentMonthProperFormat = "0" + month
                else currentMonthProperFormat = month.toString()

                var loggedUserId = loggedUser.id

                var stats = Database.getInstance().getStatsDAO().getTotalHoursAndOvertimeByMonthAndYear(loggedUserId,currentMonthProperFormat,currentYear.toString())


                tvHoursDone.text = stats.totalHours.toString() + "h"
                tvOvertimeHours.text = stats.totalOvertime.toString() + "h"
                tvHoursSundayOrNightShift.text = stats.totalSundayOrNightShift.toString() + "h"
            }

        }


        return view
    }

}