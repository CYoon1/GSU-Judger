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

class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        BELOW IS DEFAULT

        setContentView(R.layout.activity_main)

        auth = Firebase.auth
        var LogOutButton = findViewById<Button>(R.id.LogOutButton)
        var LoggedInText = findViewById<TextView>(R.id.LoggedInText)

        val currentUser = auth.currentUser
        if (currentUser == null) {
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()
        }

        LogOutButton.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()
        }
    }
}
