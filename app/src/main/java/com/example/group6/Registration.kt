package com.example.group6

import android.os.Bundle
import android.text.TextUtils
import android.widget.*
import androidx.activity.ComponentActivity
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth
import android.content.Intent
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.group6.ui.theme.Group6Theme

class Registration: ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(applicationContext, EventListener::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register)

        auth = Firebase.auth
        var RegisterText = findViewById<TextView>(R.id.RegistrationText)
        var EnterEmail = findViewById<EditText>(R.id.Email_Textbox)
        var EnterPassword = findViewById<EditText>(R.id.Password_Textbox)
        var RegisterButton = findViewById<Button>(R.id.Register_Button)
        var ProgressBar = findViewById<ProgressBar>(R.id.ProgressBar)
        var Click2Login = findViewById<Button>(R.id.Click2Login)


        Click2Login.setOnClickListener {
                val intent = Intent(applicationContext, Login::class.java)
                startActivity(intent)
                finish()

        }

        RegisterButton.setOnClickListener{
                ProgressBar.visibility = View.VISIBLE
                var email: String? = null
                var password: String? = null
                email = EnterEmail.text.toString()
                password = EnterPassword.text.toString()

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(this@Registration, "Email is empty", Toast.LENGTH_SHORT).show()
                    val intent = Intent(applicationContext, Registration::class.java)
                    startActivity(intent)

                }

                if (TextUtils.isEmpty(password)){
                    Toast.makeText(this@Registration, "Password is empty", Toast.LENGTH_SHORT).show()
                    val intent = Intent(applicationContext, Registration::class.java)
                    startActivity(intent)
                }

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        ProgressBar.visibility = View.GONE
                        if (task.isSuccessful) {
                            Toast.makeText(this@Registration, "Account created.", Toast.LENGTH_SHORT).show()
//                            val user = auth.currentUser
//                            updateUI(user)

                            val intent = Intent(applicationContext, EventListener::class.java)
                            startActivity(intent)
                            finish()

                        } else {
                            Toast.makeText(this@Registration, "Authentication failed.", Toast.LENGTH_SHORT,).show()
//                            updateUI(null)
                        }
                    }

        }

    }
}
