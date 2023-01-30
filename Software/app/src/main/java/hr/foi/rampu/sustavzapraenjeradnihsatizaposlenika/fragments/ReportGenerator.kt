package hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.fragments

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.itextpdf.text.Document
import com.itextpdf.text.Font
import com.itextpdf.text.Paragraph
import com.itextpdf.text.Phrase
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.Database
import hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.R
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import java.util.jar.Manifest


class ReportGenerator : Fragment() {

    private lateinit var btnGenerate : Button
    private lateinit var spinner : Spinner


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_report_generator, container, false)

        btnGenerate = view.findViewById(R.id.bt_generate_report)



        spinner = view.findViewById<Spinner>(R.id.sp_months)

        var myList: MutableList<String> = mutableListOf<String>("siječanj","veljača","ožujak","travanj","svibanj","lipanj","srpanj","kolovoz","rujan","listopad","studeni","prosinac")

        val current = LocalDateTime.now()
        var currentMonth = current.monthValue + 4  //+4 stavljen samo radi testiranja
        var currentYear = current.year

        btnGenerate.setOnClickListener {

            generatePdf(currentYear.toString())
        }

        myList = myList.take(currentMonth) as MutableList<String>


        val adapter = ArrayAdapter<String>(view.context,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,myList)

        spinner.adapter = adapter




        return view
    }


    private val pdfName = "Izvještaj_"+SimpleDateFormat("yyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis())+".pdf"
    private val pdfPath = Environment.getExternalStorageDirectory().toString() + "/Download/" + pdfName
    @SuppressLint("SuspiciousIndentation")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun generatePdf(year:String) {
        val document = Document()
        val activity = requireActivity()
        try {
            PdfWriter.getInstance(document, FileOutputStream(pdfPath))
            document.open()

            val current = LocalDateTime.now()
            val currentDate = current.toLocalDate().toString()



            val title = Paragraph("Izvjestaj radnih sati,"+currentDate)
            title.alignment = Paragraph.ALIGN_CENTER
            title.font.size = 24f
            title.font.isBold
            title.spacingAfter = 20f
            document.add(title)


            val activity = requireActivity()
            Database.buildInstance(activity)
           val Users = Database.getInstance().getUsersDAO().getAllUsers()
            val font = Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD)
            val table = PdfPTable(5)
            table.widthPercentage = 100f

                table.addCell(PdfPCell(Phrase("Ime",font)))
                table.addCell(PdfPCell(Phrase("Prezime",font)))
                table.addCell(PdfPCell(Phrase("Odrađeni(h)",font)))
                table.addCell(PdfPCell(Phrase("Prekovremeni(h)",font)))
                table.addCell(PdfPCell(Phrase("Noćna/Nedjelja(h)",font)))

            var currentMonthProperFormat: String
            val position = spinner.selectedItemId
            var month = position + 1
            if(month < 10) currentMonthProperFormat = "0" + month
            else currentMonthProperFormat = month.toString()

            for(user in Users){
                val name = user.name
                val surname = user.surname

                val statsOfUser = Database.getInstance().getStatsDAO().getStatsByUser(user.id,currentMonthProperFormat,year)

                table.addCell(PdfPCell(Phrase(name)))
                table.addCell(PdfPCell(Phrase(surname)))
                table.addCell(PdfPCell(Phrase(statsOfUser.totalHours.toString())))
                table.addCell(PdfPCell(Phrase(statsOfUser.totalOvertime.toString())))
                table.addCell(PdfPCell(Phrase(statsOfUser.totalSundayOrNightShift.toString())))

            }




            document.add(table)
            document.close()
            Toast.makeText(activity, "PDF created successfully", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(activity, "Error: $e", Toast.LENGTH_LONG).show()
        }
    }


}