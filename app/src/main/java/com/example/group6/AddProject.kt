package com.example.group6
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.ComponentActivity
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import android.widget.TextView
import android.widget.Button


class AddProject : ComponentActivity(){
    private lateinit var projName: EditText
    private lateinit var projDesc: EditText
    private lateinit var subBtn: Button
    private lateinit var backBtn: Button
    private var database = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.project_page)
        projName = findViewById(R.id.enterProj)
        projDesc = findViewById(R.id.enterDesc)
        subBtn = findViewById(R.id.submitProj)
        backBtn = findViewById(R.id.BackButton)

        subBtn.setOnClickListener{
            val projectName = projName.text.toString()
            val projectDesc = projDesc.text.toString()
            val projMap = hashMapOf(
                "name" to projectName,
                "description" to projectDesc
            )
            database.collection("Projects").document().set(projMap)
                .addOnSuccessListener {
                    Toast.makeText(this, "Successfully added your project", Toast.LENGTH_SHORT).show()
                    val intent = Intent(applicationContext, EventListener::class.java)
                    startActivity(intent)
                    finish()
                    //projName.text.clear()
                    //projDesc.text.clear()
                }
                .addOnFailureListener{
                    Toast.makeText(this, "Failed to added your project", Toast.LENGTH_SHORT).show()
                }
        }

        backBtn.setOnClickListener{
            val intent = Intent(applicationContext, ShowEvent::class.java)
            startActivity(intent)
            finish()
        }


    }
}