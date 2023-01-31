package hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.baza.Entities.Activity
import hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.baza.Database
import hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.baza.MockDataLoader
import hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.R
import java.text.SimpleDateFormat
import java.util.*


class CreateScheduleFragment : Fragment() {

    private lateinit var pickedDate : EditText
    private lateinit var startTime : EditText
    private lateinit var activityName : EditText
    private lateinit var endTime : EditText
    private lateinit var Unesibtn : Button
    private val selectedDateTime: Calendar = Calendar.getInstance()
    private val sdfDate = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    private val sdfTime = SimpleDateFormat("HH:mm:ss", Locale.US)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("SuspiciousIndentation", "MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_create_schedule, container, false)
        pickedDate = view.findViewById(R.id.date)
           pickedDate.setOnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    DatePickerDialog(
                        view.context,
                        { _, year, monthOfYear, dayOfMonth ->
                            selectedDateTime.set(year, monthOfYear, dayOfMonth)
                            pickedDate.setText(sdfDate.format(selectedDateTime.time).toString())
                        },
                        selectedDateTime.get(Calendar.YEAR),
                        selectedDateTime.get(Calendar.MONTH),
                        selectedDateTime.get(Calendar.DAY_OF_MONTH)
                    ).show()
                    view.clearFocus()
                }
           }
        startTime = view.findViewById(R.id.time_Start)
        startTime.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                TimePickerDialog(
                    view.context, { _, hourOfDay, minute ->
                        selectedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        selectedDateTime.set(Calendar.MINUTE, minute)
                        startTime.setText(sdfTime.format(selectedDateTime.time).toString())
                                  },
                    selectedDateTime.get(Calendar.HOUR_OF_DAY),
                    selectedDateTime.get(Calendar.MINUTE), true
                ).show()
                view.clearFocus()
            }
        }

        endTime = view.findViewById(R.id.time_end)
        endTime.setOnFocusChangeListener { view, hasFocus ->
            if (hasFocus) {
                TimePickerDialog(
                    view.context, { _, hourOfDay, minute ->
                        selectedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                        selectedDateTime.set(Calendar.MINUTE, minute)
                        endTime.setText(sdfTime.format(selectedDateTime.time).toString())
                    },
                    selectedDateTime.get(Calendar.HOUR_OF_DAY),
                    selectedDateTime.get(Calendar.MINUTE), true
                ).show()
                view.clearFocus()
            }
        }
        activityName = view.findViewById(R.id.Aktivnost)
        Unesibtn = view.findViewById(R.id.btn_insert)
        Unesibtn.setOnClickListener {
            Database.buildInstance(view.context)
            val mockDataLoader = MockDataLoader()
            mockDataLoader.loadMockData()
            var Users = Database.getInstance().getUsersDAO().getAllUsers()
            for(user in Users){
                val newActivity = arrayOf(Activity(0,activityName.text.toString(), pickedDate.text.toString() + " " + startTime.text.toString(),pickedDate.text.toString() + " " + endTime.text.toString(),user.id))
                Database.getInstance().getActivitiesDAO().insertActivity(*newActivity)
            }
            Toast.makeText(activity, "Unešena nova aktivnost!", Toast.LENGTH_LONG).show()
        }
        return view
    }
}