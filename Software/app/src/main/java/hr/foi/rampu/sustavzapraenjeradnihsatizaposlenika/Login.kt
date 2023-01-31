package hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {
    lateinit var editTextEmail: EditText
    lateinit var editTextPassword: EditText

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth
        editTextEmail = findViewById(R.id.et_email_login)
        editTextPassword = findViewById(R.id.et_password)

        Database.buildInstance(baseContext);
        var mockDataLoader = MockDataLoader()
        mockDataLoader.loadMockData()

        val gumb = findViewById<Button>(R.id.btn_registration)
        gumb.setOnClickListener {
            val intent = Intent(this, Registration::class.java)
            startActivity(intent)
        }
        val gumblogin = findViewById<Button>(R.id.btn_login)
        gumblogin.setOnClickListener {
            login()
        }

        val hyper = findViewById<TextView>(R.id.tv_password_reset)
        hyper.setOnClickListener {
            val intent = Intent(this, PasswordReset::class.java)
            startActivity(intent)
        }
    }

    fun login(){
        val email = editTextEmail.text.toString().trim();
        val password = editTextPassword.text.toString().trim()

        if(email.isEmpty()){
            editTextEmail.setError("Email je obavezan!")
            editTextEmail.requestFocus()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Unesite ispravan email!")
            editTextEmail.requestFocus()
            return
        }
        if(password.isEmpty()){
            editTextPassword.setError("Lozinka je obavezna!")
            editTextPassword.requestFocus()
            return
        }
        if(password.length < 6){
            editTextPassword.setError("Lozinka mora imati minimalno 6 znakova!")
            editTextPassword.requestFocus()
            return
        }


        var existingUser = Database.getInstance().getUsersDAO().getUserByEmail(email);
        if(existingUser != null){

            val intent: Intent
            UserData.data = email
            if(existingUser.role == 1){
                intent = Intent(this,AdminActivity::class.java)
            }
            else{
                intent = Intent(this, MainActivity::class.java)
            }
            startActivity(intent)
        }
        else {
            Toast.makeText(baseContext, "Neuspješna prijava! Provjerite ispravnost vaših podataka",
                Toast.LENGTH_LONG).show()
        }


        /*
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent: Intent
                    UserData.data = email
                    if(UserData.data == "niki.parag@gmail.com"){
                         intent = Intent(this,AdminActivity::class.java)
                    }
                    else{
                         intent = Intent(this, MainActivity::class.java)
                    }
                    startActivity(intent)
                } else {
                    Toast.makeText(baseContext, "Neuspješna prijava! Provjerite ispravnost vaših podataka",
                        Toast.LENGTH_LONG).show()
                }
            }


         */
    }


}