package hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gumb = findViewById<Button>(R.id.btn_login)

    }
}