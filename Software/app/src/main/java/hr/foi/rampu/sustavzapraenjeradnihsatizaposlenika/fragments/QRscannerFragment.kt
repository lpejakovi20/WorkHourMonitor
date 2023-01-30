package hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.fragments

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ScanMode
import hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.*
import hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.adapters.ActivitiesFragmentAdapter
import hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.adapters.MainPagerAdapter
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

private const val  CAMERA_REQUEST_CODE = 101

class QRscannerFragment : Fragment() {

    private lateinit var codeScanner: CodeScanner
    private lateinit var qrview :CodeScannerView
    private lateinit var  btntest :Button
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =inflater.inflate(R.layout.fragment_q_rscanner, container, false)
        val activity = requireActivity()
        qrview = view.findViewById<CodeScannerView>(R.id.scanner_view)
        btntest = view.findViewById(R.id.testgumb)
        setupPermissions(view,activity)
        codeScanner(view,activity)


        return view
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun codeScanner(view:View, activity: FragmentActivity){

        codeScanner = CodeScanner(view.context,qrview)

        codeScanner.apply{
            camera = CodeScanner.CAMERA_BACK
        formats = CodeScanner.ALL_FORMATS

            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.CONTINUOUS
            isAutoFocusEnabled = false
            isFlashEnabled = false

            codeScanner.decodeCallback = DecodeCallback {
                activity.runOnUiThread {
                    if(it.text =="Prijavljeni" && !QRScanData.already_scanned) {
                        unosuBazuPrijava(view)
                        Toast.makeText(activity, "Dobar dan! Uspješno ste se prijavili na posao!", Toast.LENGTH_LONG).show()
                        QRScanData.already_scanned = true
                    }
                    else if(it.text =="Prijavljeni" && QRScanData.already_scanned){
                        Toast.makeText(activity, "Doviđenja! Uspješno ste se odjavili s posla!", Toast.LENGTH_LONG).show()
                        unosuBazuOdjava()
                        QRScanData.already_scanned = false
                    }
                    else{
                        Toast.makeText(activity, "Neuspješna prijava! Krivi QR kod", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
        qrview.setOnClickListener {
            codeScanner.startPreview()
        }
        btntest.setOnClickListener {

            val currentDateTime = Calendar.getInstance().time
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val formattedDateTime = formatter.format(currentDateTime)

            QRScanData.startTime =formattedDateTime.toString()
            Database.buildInstance(view.context)
            var mockDataLoader = MockDataLoader()
            mockDataLoader.loadMockData()
            var loggedUser: User? = null
            loggedUser = Database.getInstance().getUsersDAO()
                .getUserByEmail(UserData.data.toString())
            Toast.makeText(activity, formattedDateTime.toString(), Toast.LENGTH_LONG).show()
           val stats = arrayOf(Stats(0, formattedDateTime,formattedDateTime,5,1,0,1),

            )
           Database.getInstance().getStatsDAO().insertStats(*stats)
        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    private fun setupPermissions(view: View,activity: FragmentActivity){
        val permission = ContextCompat.checkSelfPermission(view.context,android.Manifest.permission.CAMERA)

        if(permission != PackageManager.PERMISSION_GRANTED){
            makeRequest(activity)
        }
    }
    private fun  makeRequest(activity: FragmentActivity){
        ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.CAMERA),
            CAMERA_REQUEST_CODE)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun unosuBazuPrijava(view:View){
        val currentDateTime = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val formattedDateTime = formatter.format(currentDateTime)
        QRScanData.startTime =formattedDateTime.toString()
        Database.buildInstance(view.context)
        var mockDataLoader = MockDataLoader()
        mockDataLoader.loadMockData()
        var loggedUser: User? = null
        loggedUser = Database.getInstance().getUsersDAO()
            .getUserByEmail(UserData.data.toString())
        Toast.makeText(activity, formattedDateTime.toString(), Toast.LENGTH_LONG).show()
        val stats = arrayOf(Stats(0, formattedDateTime,formattedDateTime,5,1,0,1),
            )
        Database.getInstance().getStatsDAO().insertStats(*stats)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun unosuBazuOdjava(){
        val currentDateTime = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val formattedDateTime = formatter.format(currentDateTime)
        var startTime = QRScanData.startTime.toString()
        var minutes = PreracunajVrijeme(formattedDateTime,startTime)
        var mockDataLoader = MockDataLoader()
        mockDataLoader.loadMockData()
        var loggedUser: User? = null
        loggedUser = Database.getInstance().getUsersDAO()
            .getUserByEmail(UserData.data.toString())
        Database.getInstance().getStatsDAO().updateHoursDoneWithCondition(startTime,minutes,loggedUser.id)
    }
    private fun PreracunajVrijeme(Endtime:String,startTime:String):Int{
        var start = startTime.split(" ")[1]
        var startHour = start.split(":")[0]
        var startMinutes = start.split(":")[1]
        var end =  Endtime.split(" ")[1]
        var endHour = end.split(":")[0]
        var endMinutes = end.split(":")[1]

        var startduration = startHour.toInt() * 60 + startMinutes.toInt()
        var endduration = endHour.toInt() * 60 + startMinutes.toInt()
        var differences = endduration - startduration
        return differences
       /* var Finaltimehours =  (differences/60).toInt().toString()
        var FinalTimeminutes = (differences%60).toInt().toString()
        var FinalDate = Finaltimehours + FinalTimeminutes*/
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            CAMERA_REQUEST_CODE ->{
                if(grantResults.isEmpty() || grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(activity, "Potrebno je dopuštenje za kameru da možete koristiti aplikaciju", Toast.LENGTH_LONG).show()
                }
                else{
                    //successful
                }
            }
        }
    }
}