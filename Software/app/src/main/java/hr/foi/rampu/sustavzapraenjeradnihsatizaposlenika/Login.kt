package hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val gumb = findViewById<Button>(R.id.btn_registration)
        gumb.setOnClickListener {
            val intent = Intent(this, Registration::class.java)
            startActivity(intent)
        }
        val gumblogin = findViewById<Button>(R.id.btn_login)
        gumblogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val hyper = findViewById<TextView>(R.id.tv_password_reset)
        hyper.setOnClickListener {
            val intent = Intent(this, PasswordReset::class.java)
            startActivity(intent)
        }
    }
}