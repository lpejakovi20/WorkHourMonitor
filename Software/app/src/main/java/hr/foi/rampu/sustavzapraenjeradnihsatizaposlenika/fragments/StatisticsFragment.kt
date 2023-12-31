package hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.fragments

import android.os.Build
import android.os.Bundle
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
import hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.baza.Database
import hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.baza.Entities.User
import hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.baza.MockDataLoader
import java.time.LocalDateTime

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

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var month = position + 1
                if(month < 10) currentMonthProperFormat = "0" + month
                else currentMonthProperFormat = month.toString()

                var loggedUserId = loggedUser.id

                var stats = Database.getInstance().getStatsDAO().getTotalHoursAndOvertimeByMonthAndYear(loggedUserId,currentMonthProperFormat,currentYear.toString())

                var hours = stats.totalHours / 60
                var min = stats.totalHours % 60
                tvHoursDone.text = hours.toString() + "h" + " " + min.toString() + "m"

                hours = stats.totalOvertime / 60
                min = stats.totalOvertime % 60
                tvOvertimeHours.text = hours.toString() + "h" + " " + min.toString() + "m"

                hours = stats.totalSundayOrNightShift / 60
                min = stats.totalSundayOrNightShift % 60
                tvHoursSundayOrNightShift.text = hours.toString() + "h" + " " + min.toString() + "m"
            }
        }
        return view
    }
}