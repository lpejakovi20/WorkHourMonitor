package hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.fragments

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.google.firebase.database.FirebaseDatabase
import hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.R
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Environment
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.itextpdf.text.Document
import com.itextpdf.text.Image
import com.itextpdf.text.pdf.PdfWriter
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

private const val  EXTERNAL_STORAGE_REQUEST_CODE = 1002
class QRGeneratorFragment : Fragment() {

    private lateinit var btnGenerate : Button
    private lateinit var image : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_q_r_generator, container, false)

        btnGenerate = view.findViewById(R.id.btn_generate)
        btnGenerate.setOnClickListener {
          var randomString =  generateRandomString(12)
            FirebaseDatabase.getInstance().getReference("QRcode").setValue(randomString)
            val QRcode = generateQRCode(randomString)
            image = view.findViewById(R.id.imageViewQR)
            image.setImageBitmap(QRcode)
            val activity = requireActivity()
            setupPermissions(view,activity,QRcode!!)
        }
        return view

    }
    fun generateRandomString(length: Int): String {
        val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..length)
            .map { kotlin.random.Random.nextInt(0, charPool.size) }
            .map(charPool::get)
            .joinToString("")
    }

    fun generateQRCode(content: String): Bitmap? {
        val writer = MultiFormatWriter()
        try {
            val bitMatrix: BitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 512, 512)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bitmap.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
                }
            }
            return bitmap
        } catch (e: WriterException) {
            e.printStackTrace()
            return null
        }
    }
    private val pdfName = "QRKod_"+ SimpleDateFormat("yyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis())+".pdf"
    private val pdfPath = Environment.getExternalStorageDirectory().toString() + "/Download/" + pdfName

    fun addBitmapToPdf(bitmap: Bitmap) {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val image = Image.getInstance(stream.toByteArray())

        val document = Document()
        val writer = PdfWriter.getInstance(document, FileOutputStream(pdfPath))
        document.open()

        document.add(image)

        document.close()
        writer.close()
        Toast.makeText(activity, "PDF created successfully", Toast.LENGTH_SHORT).show()
    }
    private fun setupPermissions(view: View, activity: FragmentActivity,bitmap: Bitmap){
        val permission = ContextCompat.checkSelfPermission(view.context,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if(permission == PackageManager.PERMISSION_GRANTED){
            addBitmapToPdf(bitmap)

        }
        else{
            makeRequest(activity)
        }
    }

    private fun  makeRequest(activity: FragmentActivity){
        ActivityCompat.requestPermissions(activity, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
            EXTERNAL_STORAGE_REQUEST_CODE)
    }
}