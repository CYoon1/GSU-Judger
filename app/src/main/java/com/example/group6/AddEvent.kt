package com.example.group6

import android.annotation.SuppressLint
import android.content.Intent
import android.icu.text.CaseMap.Title
import android.os.Bundle
import android.text.TextUtils
import android.util.EventLogTags.Description
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import android.util.Log
import android.content.ContentValues.TAG

class AddEvent: ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseFirestore


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.add_event)

        auth = Firebase.auth

        var AddEvent = findViewById<TextView>(R.id.AddEvent)
        var TitleName = findViewById<EditText>(R.id.TitleName)
        var Desc = findViewById<EditText>(R.id.Description)
        var LastDay = findViewById<TextView>(R.id.LastDay)
        var Date = findViewById<EditText>(R.id.Date)
        var Location = findViewById<EditText>(R.id.Location)
        var AddEventButton = findViewById<Button>(R.id.AddEventButton)
        var BackButton = findViewById<Button>(R.id.BackButton)

        val currentUser = auth.currentUser
        if (currentUser == null) {
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()
        }

        BackButton.setOnClickListener{
            val intent = Intent(applicationContext, EventListener::class.java)
            startActivity(intent)
            finish()
        }

        AddEventButton.setOnClickListener{
            var EventName: String?=null
            var EventDesc: String?=null
            var EventDate: String?=null
            var EventLocation: String?=null
            database = FirebaseFirestore.getInstance()

            EventName = TitleName.text.toString()
            EventDesc = Desc.text.toString()
            EventDate = Date.text.toString()
            EventLocation = Location.text.toString()

            val event = hashMapOf(
                "EventName" to EventName,
                "EventDate" to EventDate,
                "EventLocation" to EventLocation,
                "EventDesc" to EventDesc
            )
            database.collection("EventsExample")
                .add(event)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                    val intent = Intent(applicationContext, EventListener::class.java)
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }

            if (TextUtils.isEmpty(EventName)){
                Toast.makeText(this@AddEvent, "Title is empty", Toast.LENGTH_SHORT).show()
            }

            if (TextUtils.isEmpty(EventDate)){
                Toast.makeText(this@AddEvent, "Date is empty", Toast.LENGTH_SHORT).show()
            }
        }

    }
}