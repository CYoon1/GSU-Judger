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

class Login: ComponentActivity() {
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

        setContentView(R.layout.activity_login)

        auth = Firebase.auth
        var PleaseLogin = findViewById<TextView>(R.id.Please_Login)
        var EnterEmail = findViewById<EditText>(R.id.Email_Textbox)
        var EnterPassword = findViewById<EditText>(R.id.Password_Textbox)
        var LoginButton = findViewById<Button>(R.id.Login_Button)
        var RegisterNewAcc = findViewById<Button>(R.id.Go_To_Register)
        var ProgressBar = findViewById<ProgressBar>(R.id.ProgressBar)
        RegisterNewAcc.setOnClickListener {
            val intent = Intent(applicationContext, Registration::class.java)
            startActivity(intent)
            finish()

        }

        LoginButton.setOnClickListener{
            ProgressBar.visibility = View.VISIBLE
            var email: String? = null
            var password: String? = null
            email = EnterEmail.text.toString()
            password = EnterPassword.text.toString()

            if (TextUtils.isEmpty(email)){
                Toast.makeText(this@Login, "Email is empty", Toast.LENGTH_SHORT).show()
                val intent = Intent(applicationContext, Login::class.java)
                startActivity(intent)
            }

            if (TextUtils.isEmpty(password)){
                Toast.makeText(this@Login, "Password is empty", Toast.LENGTH_SHORT).show()
                val intent = Intent(applicationContext, Login::class.java)
                startActivity(intent)
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    ProgressBar.visibility = View.GONE

                    if (task.isSuccessful) {

                        Toast.makeText(this@Login, "Login success.", Toast.LENGTH_SHORT).show()
                        val intent = Intent(applicationContext, EventListener::class.java)
                        startActivity(intent)
                        finish()
                        // Sign in success, update UI with the signed-in user's information
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(baseContext, "Login failed.", Toast.LENGTH_SHORT).show()
                        val intent = Intent(applicationContext, Login::class.java)
                        startActivity(intent)
                        finish()
                    }
                }

        }


    }


}