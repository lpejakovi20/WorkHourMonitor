package hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class PasswordReset : AppCompatActivity() {

    lateinit var email: EditText
    lateinit var resetPasswordButton: Button
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_reset)

        email = findViewById(R.id.et_reset_password)
        resetPasswordButton = findViewById(R.id.btn_reset_password)

        auth = Firebase.auth

        resetPasswordButton.setOnClickListener{
            resetPassword()
        }

    }

    fun resetPassword(){
        var email_content: String = email.text.toString().trim()

        if(email_content.isEmpty()){
            email.error = "Potrebno je upisati E-mail!"
            return
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email_content).matches()){
            email.error = "E-mail nije upisan u točnom formatu!"
            return
        }

        auth.sendPasswordResetEmail(email_content).addOnCompleteListener{
            Toast.makeText(baseContext,"Poslan vam je mail!",Toast.LENGTH_SHORT)
                .show()
        }

    }
}