package hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.baza.User

class Registration : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var name: EditText
    private lateinit var surname: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var btnregister: Button
    private lateinit var progressbar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        name = findViewById(R.id.et_name)
        surname = findViewById(R.id.et_surname)
        email = findViewById(R.id.et_email)
        password = findViewById(R.id.et_password)
        btnregister = findViewById(R.id.btn_regse)
        progressbar = findViewById(R.id.progressBar)

        auth = Firebase.auth
        btnregister.setOnClickListener{
            register()
        }
    }

    private fun register() {
        var name_content: String = name.text.toString().trim()
        var surname_content: String = surname.text.toString().trim()
        var email_content: String = email.text.toString().trim()
        var password_content: String = password.text.toString().trim()

        if(name_content.isEmpty()){
            name.error = "Potrebno je upisati ime!"
            return
        }
        if(surname_content.isEmpty()){
            surname.error = "Potrebno je upisati prezime!"
            return
        }
        if(email_content.isEmpty()){
            email.error = "Potrebno je upisati E-mail!"
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email_content).matches()){
            email.error = "E-mail nije upisan u točnom formatu!"
            return
        }
        if(password_content.isEmpty()){
            password.error = "Potrebno je upisati lozinku!"
            return
        }
        if(password_content.length < 6){
            password.error = "Vaša lozinka mora imati minimalno 6 znakova!"
            return
        }

        auth.createUserWithEmailAndPassword(email_content,password_content)
            .addOnCompleteListener{task ->
                if(task.isSuccessful){
                    val user = User(name_content,surname_content,email_content,password_content)

                    FirebaseDatabase.getInstance().getReference("Users")
                        .child(Firebase.auth.currentUser!!.uid).setValue(user)
                        .addOnCompleteListener { task ->
                            if(task.isSuccessful){
                                Toast.makeText(baseContext,"Uspješno ste se registrirali",Toast.LENGTH_LONG).show()
                                val intent = Intent(this, Login::class.java)
                                startActivity(intent)
                            }else{
                                Toast.makeText(baseContext,"Pogreška kod registracije",Toast.LENGTH_LONG).show()
                            }
                        }
                }else{
                    Toast.makeText(baseContext,"Pogreška kod registracije",Toast.LENGTH_LONG).show()
                }
            }


    }
}