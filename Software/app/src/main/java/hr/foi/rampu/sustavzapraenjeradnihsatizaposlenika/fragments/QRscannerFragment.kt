package hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.fragments

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ScanMode
import hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.MainActivity
import hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.R
import java.util.Scanner

private const val  CAMERA_REQUEST_CODE = 101

class QRscannerFragment : Fragment() {

    private lateinit var codeScanner: CodeScanner
    private lateinit var qrview :CodeScannerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =inflater.inflate(R.layout.fragment_q_rscanner, container, false)
        val activity = requireActivity()
        qrview = view.findViewById<CodeScannerView>(R.id.scanner_view)
        setupPermissions(view,activity)
        codeScanner(view,activity)
        return view
    }
    private fun codeScanner(view:View,activity: FragmentActivity){

        codeScanner = CodeScanner(view.context,qrview)

        codeScanner.apply{
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS

            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.CONTINUOUS
            isAutoFocusEnabled = true
            isFlashEnabled = false

            codeScanner.decodeCallback = DecodeCallback {
                activity.runOnUiThread {
                    Toast.makeText(activity, it.text, Toast.LENGTH_LONG).show()
                }
            }
        }
        qrview.setOnClickListener {
            codeScanner.startPreview()
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